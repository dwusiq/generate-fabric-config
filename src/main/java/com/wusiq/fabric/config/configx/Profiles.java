package com.wusiq.fabric.config.configx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Policy entity of configx.yaml config.
 */
@Data
public class Profiles {
    @JsonProperty(value = "ApplicationChannel")
    private Channel applicationChannel;
    //support solo/kafka/etcdraft
    @JsonProperty(value = "OrdererGenesis")
    private OrdererGenesis ordererGenesis;
}