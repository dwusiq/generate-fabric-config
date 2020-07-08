package com.wusiq.fabric.config.container;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CaYaml extends BaseFabric {
    private Integer caPort;
    private String caCryptoPath;
    private String caCertFileName;
    private String caPrivateKeyFileName;
}
