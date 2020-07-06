package com.wusiq.fabric.config.configx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Node entity of configx.yaml config.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {
    @JsonProperty(value = "Host")
    private String host;
    @JsonProperty(value = "Port")
    private Integer port;
}
