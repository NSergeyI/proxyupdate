package com.yardox.proxyupdate.checker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yardox.proxyupdate.parser.dto.foxtools.MainClassResponse;
import com.yardox.proxyupdate.persistence.model.MyProxy;
import com.yardox.proxyupdate.persistence.service.IMyProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

@Service
public class CheckService {

    private ArrayList<MyProxy> proxies;

    @Autowired
    private IMyProxyService myProxyService;

    public CheckService() {
    }

    public CheckService(ArrayList<MyProxy> proxies) {
        this.proxies = proxies;
    }

    public ArrayList<MyProxy> checkMyProxy() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("x-request-id", "6869641d-6d5f-4ff1-9c5e-765650731ad0");
        headers.set("accept-language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7,de;q=0.6,ka;q=0.5");
        headers.set("x-forwarded-proto", "http");
        headers.set("connect-time", "0");
        headers.set("x-forwarded-port", "80");
        headers.set("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        headers.set("via", "1.1 vegur");
        headers.set("total-route-time", "0");
        headers.set("host", "safe-brook-68226.herokuapp.com");
        headers.set("upgrade-insecure-requests", "1");
        headers.set("x-request-start", "1654927188437");
        headers.set("connection", "close");
        headers.set("cache-control", "max-age=0");
        headers.set("accept-encoding", "gzip, deflate");
        headers.set("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36");



//        headers.set("sec-fetch-mode", "navigate");
//        headers.set("x-request-id", "c3ec85d3-3cb5-499c-80c8-65d4ae01d730");
//        headers.set("sec-fetch-site", "none");
//        headers.set("accept-language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7,de;q=0.6,ka;q=0.5");
//        headers.set("x-forwarded-proto", "https");
//        headers.set("connect-time", "0");
//        headers.set("x-forwarded-port", "443");
//        headers.set("sec-fetch-user", "?1");
//        headers.set("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
//        headers.set("via", "1.1 vegur");
//        headers.set("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"102\", \"Google Chrome\";v=\"102\"");
//        headers.set("sec-ch-ua-mobile", "?0");
//        headers.set("total-route-time", "0");
//        headers.set("sec-ch-ua-platform", "\"Windows\"");
//        headers.set("host", "safe-brook-68226.herokuapp.com");
//        headers.set("upgrade-insecure-requests", "1");
//        headers.set("x-request-start", "1654869404170");
//        headers.set("connection", "close");
//        headers.set("accept-encoding", "gzip, deflate, br");
//        headers.set("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36");
//        headers.set("sec-fetch-dest", "document");

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        for (MyProxy myProxy : proxies) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(myProxy.getHost(), myProxy.getPort()));
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setProxy(proxy);
            RestTemplate restTemplate = new RestTemplate(requestFactory);
//            RestTemplate restTemplate = new RestTemplate();
            try {
                ResponseEntity<String> responseEntity = restTemplate.exchange("http://safe-brook-68226.herokuapp.com/ip", HttpMethod.GET, entity, String.class);
                System.out.println("+++++++++++++++++++");
                String json = responseEntity.getBody();
//                ObjectMapper objectMapper = new ObjectMapper();
                System.out.println(json);
//                DtoIpResopnse dtoIpResopnse = objectMapper.readValue(json, DtoIpResopnse.class);
//                System.out.println(myProxy.getHost() + " " + dtoIpResopnse.getIp());
//                System.out.println(dtoIpResopnse);
            }
//            catch (RestClientException | JsonProcessingException | NoSuchElementException e) {
            catch (RestClientException | NoSuchElementException e) {
                System.out.println("****************");
                System.out.println(myProxy.getHost() + " " + e.getMessage());
//                e.printStackTrace();
            }
        }
        return proxies;
    }

    public void getProxiesFromBD() {
        proxies = (ArrayList<MyProxy>) myProxyService.findAll();
    }
}
