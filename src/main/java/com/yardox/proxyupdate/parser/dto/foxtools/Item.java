package com.yardox.proxyupdate.parser.dto.foxtools;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = false)
@Getter
public class Item {
    @JsonProperty("ip")
    public String ip;
    @JsonProperty("port")
    public int port;
    @JsonProperty("type")
    public int type;
    @JsonProperty("anonymity")
    public String anonymity;
    @JsonProperty("uptime")
    public float uptime;
    @JsonProperty("checked")
    public String checked;
    @JsonProperty("available")
    public String available;
    @JsonProperty("free")
    public String free;
    @JsonProperty("country")
    public Country country;

    @Override
    public String toString() {
        return "Item{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", type=" + type +
                ", anonymity='" + anonymity + '\'' +
                ", uptime=" + uptime +
                ", checked='" + checked + '\'' +
                ", available='" + available + '\'' +
                ", free='" + free + '\'' +
                ", country=" + country +
                '}';
    }
}
