package com.wusiq.fabric.config.configx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Application entity of configx.yaml config.
 */
@Data
public class Application {
    @JsonProperty(value = "Policies")
    private Map<String, Policy> policies;
    @JsonProperty(value = "Capabilities")
    private Map<String, Boolean> capabilities;
    @JsonProperty(value = "Organizations")
    private List<Organization> organizations;
}
