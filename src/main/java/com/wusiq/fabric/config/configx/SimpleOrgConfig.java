package com.wusiq.fabric.config.configx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class SimpleOrgConfig {
    @JsonProperty(value = "Name")
    public String name;
    @JsonProperty(value = "ID")
    public String iD;
    @JsonProperty(value = "MSPDir")
    public String mSPDir;
    @JsonProperty(value = "Policies")
    public Map<String,SimplePolicy> policies;
}
