package com.yardox.proxyupdate.parser;

import com.yardox.proxyupdate.persistence.model.MyProxy;
import com.yardox.proxyupdate.persistence.model.ProxyType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserFoxtoolsRU implements Parser {
    @Override
    public List<MyProxy> getProxies() throws IOException {
        List<MyProxy> result = new ArrayList<>();
        Document doc = Jsoup.connect("http://foxtools.ru/Proxy").get();
        System.out.println(doc.title());
        Elements rows = doc.select("#theProxyList tr");
        for (Element row : rows) {
            Elements tds = row.select("td");
            if (tds.size() > 4) {
                List<String> data = tds.eachText();
                String host = data.get(0);
                Integer port = Integer.valueOf(data.get(1));
                ProxyType type = ProxyType.valueOf(data.get(4));
                result.add(new MyProxy(host,port, type));
            }
        }
    return result;
    }
}
