package com.wusiq.fabric.config.configx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class ChannelConfig {
    @JsonProperty(value = "Consortium")
    private String consortium;
    @JsonProperty(value = "Policies")
    private Map<String, SimplePolicy> policies;
    @JsonProperty(value = "Capabilities")
    private Map<String, Boolean> capabilities;
}
