package com.wusiq.fabric.config.configtx;

import lombok.Data;

import java.util.List;

@Data
public class ConfigtxYaml {
    private String ordererType;
    private List<Organization> organizations;
    private List<EtcdRaftConsenter> consenters;
    private List<Address> brokers;
    private String capabilityVersion;

}
