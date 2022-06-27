package com.yardox.proxyupdate.checker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yardox.proxyupdate.persistence.model.MyProxy;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;
import java.util.NoSuchElementException;


public class IpUtilities {

    private static final String HTTP_SERVER = "http://safe-brook-68226.herokuapp.com/ip";

    private static HttpHeaders getHeaders() {
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

        return headers;

    }

    private static Proxy.Type getType(MyProxy myProxy) {
        switch (myProxy.getProxyType()) {
            case HTTP:
                return Proxy.Type.HTTP;
            case HTTPS:
                return Proxy.Type.HTTP;
            case SOCKS:
                return Proxy.Type.SOCKS;
            case SOCKS4:
                return Proxy.Type.SOCKS;
            case SOCKS5:
                return Proxy.Type.SOCKS;
            case UNKNOWN:
                return Proxy.Type.HTTP;
            case ALL:
                return Proxy.Type.HTTP;
            default:
                return Proxy.Type.HTTP;
        }
    }

    public static String getMyIp(MyProxy myProxy) {
        Proxy.Type currentType = getType(myProxy);
        Proxy proxy = new Proxy(currentType, new InetSocketAddress(myProxy.getHost(), myProxy.getPort()));
        return getIpWithStandartProxy(proxy);
    }

    public static String getMyIp() {
        Proxy proxy = Proxy.NO_PROXY;
        return getIpWithStandartProxy(proxy);
    }

    private static String getIpWithStandartProxy(Proxy proxy){
        HttpHeaders headers = getHeaders();
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setProxy(proxy);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(HTTP_SERVER, HttpMethod.GET, entity, String.class);
            String json = responseEntity.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            DtoIpResopnse dtoIpResopnse = objectMapper.readValue(json, DtoIpResopnse.class);
            return dtoIpResopnse.getIp();
        } catch (RestClientException | NoSuchElementException | JsonProcessingException e) {
            return "";
        }
    }
}