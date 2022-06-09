package com.yardox.proxyupdate.parser;

import com.yardox.proxyupdate.persistence.model.MyProxy;

import java.io.IOException;
import java.util.List;

public interface Parser {
    public List<MyProxy> getProxies() throws IOException;
}
