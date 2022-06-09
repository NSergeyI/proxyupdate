package com.yardox.proxyupdate.persistence.service;

import com.yardox.proxyupdate.persistence.model.MyProxy;

import java.util.List;

public interface IMyProxyService {
    List<MyProxy> findAll();
    void saveAll(List<MyProxy> proxies);
}
