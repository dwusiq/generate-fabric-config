package com.wusiq.fabric.config.crypto;

import lombok.Data;

import java.util.List;

/**
 * OrdererOrg entity of crypto-config.yaml config.
 */
@Data
public class OrdererOrg {
    private String name;
    private String domain;
    private Boolean enableNodeOUs;
    private List<String> hostNames;
}