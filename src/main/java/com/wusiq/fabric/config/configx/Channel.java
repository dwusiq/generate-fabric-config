package com.wusiq.fabric.config.configx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/**
 * Channel entity of configx.yaml config.
 */
@Data
public class Channel {
    @JsonProperty(value = "Consortium")
    private String consortium;
    @JsonProperty(value = "Policies")
    private Map<String, Policy> policies;
    @JsonProperty(value = "Capabilities")
    private Map<String, Boolean> capabilities;
    @JsonProperty(value = "Application")
    private Application application;
}
