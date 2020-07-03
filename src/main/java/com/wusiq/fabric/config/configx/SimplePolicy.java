package com.wusiq.fabric.config.configx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimplePolicy {
    @JsonProperty(value = "Type")
    private String type;
    @JsonProperty(value = "Rule")
    private String rule;

    public static SimplePolicy init(String type,String rule){
        return new SimplePolicy(type,rule);
    }
}