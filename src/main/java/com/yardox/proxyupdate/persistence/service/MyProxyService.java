package com.yardox.proxyupdate.persistence.service;

import com.yardox.proxyupdate.persistence.model.MyProxy;
import com.yardox.proxyupdate.persistence.repository.MyProxyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    @Override
    public void saveAll(List<MyProxy> proxies) {
        repository.saveAll(proxies);
    }
}
