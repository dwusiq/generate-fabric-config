package com.wusiq.fabric.config.configtx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Node entity of configtx.yaml config.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String host;
    private Integer port;
}
