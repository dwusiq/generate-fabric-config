package com.wusiq.fabric.config.configx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Policy entity of configx.yaml config.
 */
@Data
public class Profiles {
    @JsonProperty(value = "ordererGenesis")
    private OrdererGenesis OrdererGenesis;
    @JsonProperty(value = "ChannelConfig")
    private Channel channelConfig;
}