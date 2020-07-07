package com.wusiq.fabric.config.crypto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * OrdererOrg entity of crypto-config.yaml config.
 */
@Data
public class OrdererOrg {
    @JsonProperty(value = "Name")
    private String name;
    @JsonProperty(value = "Domain")
    private String domain;
    @JsonProperty(value = "EnableNodeOUs")
    private Boolean enableNodeOUs;
    @JsonProperty(value = "Specs")
    private List<Map<String, String>> specs;
}