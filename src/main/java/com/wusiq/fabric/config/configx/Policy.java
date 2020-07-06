package com.wusiq.fabric.config.configx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Policy entity of configx.yaml config.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Policy {
    @JsonProperty(value = "Type")
    private String type;
    @JsonProperty(value = "Rule")
    private String rule;

    public static Policy init(String type, String rule) {
        return new Policy(type, rule);
    }
}