package com.wusiq.fabric.config.configx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * OrdererGenesis entity of configx.yaml config.
 */
@Data
public class OrdererGenesis {
    @JsonProperty(value = "Policies")
    private Map<String, Policy> policies;
    @JsonProperty(value = "Capabilities")
    private Map<String, Boolean> capabilities;
    @JsonProperty(value = "Orderer")
    private Orderer orderer;
    @JsonProperty(value = "Consortiums")
    private Map<String, Consortium> consortiums;
}
