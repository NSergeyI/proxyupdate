package com.yardox.proxyupdate.repository;

import com.yardox.proxyupdate.model.MyProxy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyProxyRepository extends CrudRepository<MyProxy, Long>{
}
