package com.yardox.proxyupdate.model;

import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "proxy")
public class MyProxy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String host;
    private Integer port;

    @Enumerated(EnumType.STRING)
    private ProxyType proxyType;

    public MyProxy() {
    }

    public MyProxy(Long id, String host, Integer port, ProxyType proxyType) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.proxyType = proxyType;
    }

    public Long getId() {
        return id;
    }

    public Integer getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public ProxyType getProxyType() {
        return proxyType;
    }

    public void setProxyType(ProxyType type) {
        this.proxyType = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyProxy myProxy = (MyProxy) o;
        return Objects.equals(id, myProxy.id) &&
                Objects.equals(host, myProxy.host) &&
                Objects.equals(port, myProxy.port) &&
                proxyType == myProxy.proxyType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, host, port, proxyType);
    }

    @Override
    public String toString() {
        return "MyProxy{" +
                "id=" + id +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", proxytype=" + proxyType +
                '}';
    }
}
