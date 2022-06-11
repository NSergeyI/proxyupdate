package com.yardox.proxyupdate.checker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class DtoIpResopnse {
    @JsonProperty("id")
    public int id;
    @JsonProperty("ip")
    public String ip;
    @JsonProperty("headers")
    public HashMap<String, String> headers;

    @Override
    public String toString() {
        return "DtoIpResopnse{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", headers=" + headers +
                '}';
    }
}
