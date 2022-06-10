package com.yardox.proxyupdate.parser.dto.foxtools;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


@JsonIgnoreProperties(ignoreUnknown = false)
@Getter
public class Country {
    @JsonProperty("nameEn")
    public String nameEn;
    @JsonProperty("nameRu")
    public String nameRu;
    @JsonProperty("iso3166a2")
    public String iso3166a2;

    @Override
    public String toString() {
        return "Country{" +
                "nameEn='" + nameEn + '\'' +
                ", nameRu='" + nameRu + '\'' +
                ", iso3166a2='" + iso3166a2 + '\'' +
                '}';
    }
}
