package com.yardox.proxyupdate.persistence.repository;

import com.yardox.proxyupdate.persistence.model.MyProxy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyProxyRepository extends CrudRepository<MyProxy, Long>{
}
