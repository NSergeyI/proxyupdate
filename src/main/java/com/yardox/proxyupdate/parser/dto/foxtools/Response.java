package com.yardox.proxyupdate.parser.dto.foxtools;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = false)
@Getter
public class Response {
    @JsonProperty("pageNumber")
    public int pageNumber;
    @JsonProperty("pageCount")
    public int pageCount;
    @JsonProperty("items")
    public Item[] items;

    @Override
    public String toString() {
        return "Response{" +
                "pageNumber=" + pageNumber +
                ", pageCount=" + pageCount +
                ", items=" + Arrays.toString(items) +
                '}';
    }
}
