package com.wusiq.fabric.config.crypto;

import lombok.Data;

/**
 * PeerOrgs entity of crypto-config.yaml config.
 */
@Data
public class PeerOrg {
    private String name;
    private String domain;
    private Integer peerCount;
    private Integer userCount;
    private Boolean enableNodeOUs;
}
