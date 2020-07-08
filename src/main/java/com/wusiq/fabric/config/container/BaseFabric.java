package com.wusiq.fabric.config.container;

import lombok.Data;

import java.util.List;

@Data
public class BaseFabric {
    private String serviceHost;
    private String containerName;
    private String imageTag;
    private Boolean tlsEnabled;
    private List<String> extraHosts;
}
