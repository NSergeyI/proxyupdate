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

public class ParserHideName implements Parser {
    @Override
    public List<MyProxy> getProxies() throws IOException {
        List<MyProxy> result = new ArrayList<>();
        int count = 0;
        Document doc;
        do {
            String adr = "https://hidemy.name/ru/proxy-list/?start=" + count * 64 + "#list";
            doc = Jsoup.connect(adr).get();
            System.out.println(adr);
            System.out.println(count);
            Elements rows = doc.select(".table_block tbody tr");
            for (Element row : rows) {
                Elements tds = row.select("td");
                if (tds.size() > 4) {
                    List<String> data = tds.eachText();
                    String host = data.get(0);
                    Integer port = Integer.valueOf(data.get(1));
                    try {
                        for (String typeRaw : data.get(3).split(",")) {
//                            System.out.println("++++++++++");
//                            System.out.println(data);
                            ProxyType type = ProxyType.valueOf(typeRaw.trim());
                            MyProxy myProxy = new MyProxy(host, port, type);
                            result.add(myProxy);
//                            System.out.println(myProxy);
                        }
                    } catch (IllegalArgumentException e) {
                        for (String typeRaw : data.get(4).split(",")) {
//                            System.out.println("----------");
//                            System.out.println(data);
                            ProxyType type = ProxyType.valueOf(typeRaw.trim());
                            MyProxy myProxy = new MyProxy(host, port, type);
                            result.add(myProxy);
//                            System.out.println(myProxy);
                        }
                    }
                }
            }
            count++;
            System.out.println("----------");
            System.out.println(doc.select(".next_array").size());
        } while (doc.select(".next_array").size() != 0);
        System.out.println("##############");
        return result;
    }
}
