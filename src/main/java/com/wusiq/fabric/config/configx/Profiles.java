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
    @JsonProperty(value = "SoloOrdererGenesis")
    private OrdererGenesis soloOrdererGenesis;
    @JsonProperty(value = "KafkaOrdererGenesis")
    private OrdererGenesis kafkaOrdererGenesis;
    @JsonProperty(value = "EtcdRaftOrdererGenesis")
    private OrdererGenesis etcdRaftOrdererGenesis;
}