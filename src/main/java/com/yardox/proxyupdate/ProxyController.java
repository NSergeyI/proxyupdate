package com.yardox.proxyupdate;

import com.yardox.proxyupdate.checker.CheckService;
import com.yardox.proxyupdate.parser.ParserHideName;
import com.yardox.proxyupdate.persistence.model.MyProxy;
import com.yardox.proxyupdate.persistence.service.IMyProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProxyController {

    @Autowired
    private IMyProxyService myProxyService;
    @Autowired
    private CheckService checkService;
    @Autowired
    private DiskService diskService;

    @GetMapping("/showProxies")
    public String findProxy(Model model) {
        List<MyProxy> myProxies = (List<MyProxy>) myProxyService.findAll();
        model.addAttribute("proxies", myProxies);
        return "showProxies";
    }

    @GetMapping("/parse")
    public String parse(Model model) {
//        ParserFoxtoolsRU parserFoxtoolsRU = new ParserFoxtoolsRU();
        ParserHideName parserFoxtoolsRU = new ParserHideName();
        List<MyProxy> proxies = new ArrayList<>();
        try {
            proxies = parserFoxtoolsRU.getProxies();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!proxies.isEmpty()){
            System.out.println("1111111  "+proxies.size());
            diskService.save(proxies);
//            myProxyService.saveAll(proxies);
            proxies = diskService.read();
            System.out.println("2222222222");
        }
        return "parse";
    }

    @GetMapping("/test")
    public String test(Model model){
        ArrayList<MyProxy> read = diskService.read();
        for (MyProxy proxy: read){
            System.out.println(proxy);
        }
//        System.out.println(read);
        return "parse";
    }

    @GetMapping("/check")
    public String check(Model model) {
        checkService.getProxiesFromBD();
        checkService.checkMyProxies();
    return "check";
    }
}
