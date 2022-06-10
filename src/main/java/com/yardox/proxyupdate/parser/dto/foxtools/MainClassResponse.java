package com.yardox.proxyupdate.parser.dto.foxtools;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = false)
@Getter
public class MainClassResponse {
    @JsonProperty("response")
    public Response response;

    @Override
    public String toString() {
        return "MainClassResponse{" +
                "response=" + response +
                '}';
    }
}