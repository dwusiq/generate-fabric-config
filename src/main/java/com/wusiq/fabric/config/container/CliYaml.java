package com.wusiq.fabric.config.container;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class CliYaml extends BaseFabric {
    private String systemChannelName;
    private List<String> dependOnHosts;
}

