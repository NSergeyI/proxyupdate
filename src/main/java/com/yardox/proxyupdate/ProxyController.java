package com.yardox.proxyupdate;

import com.yardox.proxyupdate.checker.CheckService;
import com.yardox.proxyupdate.parser.ParserFoxtoolsRU;
import com.yardox.proxyupdate.persistence.model.MyProxy;
import com.yardox.proxyupdate.persistence.model.ProxyType;
import com.yardox.proxyupdate.persistence.service.IMyProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProxyController {

    @Autowired
    private IMyProxyService myProxyService;
    @Autowired
    private CheckService checkService;

    @GetMapping("/showProxies")
    public String findProxy(Model model) {
        List<MyProxy> myProxies = (List<MyProxy>) myProxyService.findAll();
        model.addAttribute("proxies", myProxies);
        return "showProxies";
    }

    @GetMapping("/parse")
    public String parse(Model model) {
        ParserFoxtoolsRU parserFoxtoolsRU = new ParserFoxtoolsRU();
        List<MyProxy> proxies = new ArrayList<>();
        try {
            proxies = parserFoxtoolsRU.getProxies();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!proxies.isEmpty()){
            myProxyService.saveAll(proxies);
        }
        return "parse";
    }


    @GetMapping("/check")
    public String check(Model model) {
        checkService.getProxiesFromBD();
        checkService.checkMyProxy();
    return "check";
    }
}
