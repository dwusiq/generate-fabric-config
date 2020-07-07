package com.wusiq.fabric.config.crypto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * entity of crypto-config.yaml.
 */
@Data
public class Crypto {
    @JsonProperty(value = "OrdererOrgs")
    private List<OrdererOrg> ordererOrgs;
    @JsonProperty(value = "PeerOrgs")
    private List<PeerOrg> peerOrgs;
}
