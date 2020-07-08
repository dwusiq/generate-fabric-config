package com.wusiq.fabric.config.configtx;

import lombok.Data;

import java.util.List;

/**
 * Organization entity of configtx.yaml config.
 */
@Data
public class Organization {
    public String orgType;//peer、orderer
    public String name;
    public String mspId;
    public String mspDir;
    public List<Address> anchorPeers;
}
