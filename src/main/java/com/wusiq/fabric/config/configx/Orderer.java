package com.wusiq.fabric.config.configx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * Orderer entity of configx.yaml config.
 */
@Data
@Accessors(chain= true)
public class Orderer {
    @JsonProperty(value = "OrdererType")
    private String ordererType;
    @JsonProperty(value = "Addresses")
    private List<Address> addresses;
    @JsonProperty(value = "BatchTimeout")
    private String BatchTimeout;  // default:2s
    @JsonProperty(value = "BatchSize")
    private Map<String, Object> batchSize;
    @JsonProperty(value = "MaxChannels")
    private Integer maxChannels;
    @JsonProperty(value = "Policies")
    private Map<String, Policy> policies;
    @JsonProperty(value = "Organizations")
    private List<Organization> organizations;
    @JsonProperty(value = "Capabilities")
    private Map<String, Boolean> capabilities;
    @JsonProperty(value = "Kafka")
    private Map<String, List<Address>> Kafka;
    @JsonProperty(value = "EtcdRaft")
    private Map<String, List<EtcdRaftConsenter>> etcdRaft;
}