package com.yardox.proxyupdate.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yardox.proxyupdate.parser.dto.foxtools.Item;
import com.yardox.proxyupdate.parser.dto.foxtools.MainClassResponse;
import com.yardox.proxyupdate.persistence.model.MyProxy;
import com.yardox.proxyupdate.persistence.model.ProxyType;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParserFoxtoolsRU implements Parser {
    @Override
    public List<MyProxy> getProxies() throws IOException {
        List<MyProxy> result = new ArrayList<>();
//        Document doc = Jsoup.connect("http://foxtools.ru/Proxy").get();
//        System.out.println(doc.title());
//        Elements rows = doc.select("#theProxyList tr");
//        for (Element row : rows) {
//            Elements tds = row.select("td");
//            if (tds.size() > 4) {
//                List<String> data = tds.eachText();
//                String host = data.get(0);
//                Integer port = Integer.valueOf(data.get(1));
//                ProxyType type = ProxyType.valueOf(data.get(4));
//                result.add(new MyProxy(host,port, type));
//            }
//        }
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://api.foxtools.ru/v2/Proxy", HttpMethod.GET, entity, String.class);
        String json = responseEntity.getBody();
        System.out.println(json);
        System.out.println("++++++++");
        ObjectMapper objectMapper = new ObjectMapper();
        MainClassResponse mainClassResponse = objectMapper.readValue(json, MainClassResponse.class);
        for(Item dtoProxy: mainClassResponse.getResponse().getItems()){
            result.add(new MyProxy(dtoProxy.getIp(), Integer.valueOf(dtoProxy.getPort()),getType(dtoProxy.getType())));
        }
    return result;
    }

    private ProxyType getType(int index){
        switch (index){
            case 0: return ProxyType.UNKNOWN;
            case 1 : return ProxyType.HTTP;
            case 2 : return ProxyType.HTTPS;
            case 4 : return ProxyType.SOCKS;
            case 8 : return ProxyType.SOCKS5;
            case 15 : return ProxyType.ALL;
        }
        return ProxyType.UNKNOWN;
    }
}
