package com.yardox.proxyupdate;

import com.yardox.proxyupdate.model.MyProxy;
import com.yardox.proxyupdate.service.IMyProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@Controller
public class ProxyController {

    @Autowired
    private IMyProxyService myProxyService;

    @GetMapping("/showProxies")
    public String findProxy(Model model) {
        List<MyProxy> myProxies = (List<MyProxy>) myProxyService.findAll();
        model.addAttribute("proxies", myProxies);
        return "showProxies";
    }
}
