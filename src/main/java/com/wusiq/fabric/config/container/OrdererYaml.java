package com.wusiq.fabric.config.container;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
public class OrdererYaml extends BaseFabric {
    private Integer ordererPort;
    private String mspId;
    private String mspPath;
    private String tlsPath;
}