package com.yardox.proxyupdate.service;

import com.yardox.proxyupdate.model.MyProxy;
import com.yardox.proxyupdate.repository.MyProxyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyProxyService implements IMyProxyService{

    @Autowired
    private MyProxyRepository repository;

    @Override
    public List<MyProxy> findAll() {
        List<MyProxy> proxies = (List<MyProxy>) repository.findAll();
        return proxies;
    }
}
