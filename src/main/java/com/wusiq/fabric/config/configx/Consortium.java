package com.wusiq.fabric.config.configx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Consortium entity of configx.yaml config.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consortium {
    @JsonProperty(value = "Organizations")
    private List<Organization> organizations;
}
