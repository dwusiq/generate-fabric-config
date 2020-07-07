package com.wusiq.fabric.config.crypto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * PeerOrgs entity of crypto-config.yaml config.
 */
@Data
public class PeerOrg {
    @JsonProperty(value = "Name")
    private String name;
    @JsonProperty(value = "Domain")
    private String domain;
    @JsonProperty(value = "EnableNodeOUs")
    private Boolean enableNodeOUs;
    @JsonProperty(value = "Template")
    private Map<String,Integer> template;
}
