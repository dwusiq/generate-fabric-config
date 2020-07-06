package com.wusiq.fabric.config.configx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Organization entity of configx.yaml config.
 */
@Data
public class Organization {
    @JsonProperty(value = "Name")
    public String name;
    @JsonProperty(value = "ID")
    public String id;
    @JsonProperty(value = "MSPDir")
    public String mspDir;
    @JsonProperty(value = "Policies")
    public Map<String, Policy> policies;
    @JsonProperty(value = "AnchorPeers")
    public List<Node> anchorPeers;
}
