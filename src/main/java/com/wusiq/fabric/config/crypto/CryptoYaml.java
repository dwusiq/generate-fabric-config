package com.wusiq.fabric.config.crypto;

import lombok.Data;

import java.util.List;

/**
 * entity of crypto-config.yaml.
 */
@Data
public class CryptoYaml {
    private List<OrdererOrg> ordererOrgs;
    private List<PeerOrg> peerOrgs;
}
