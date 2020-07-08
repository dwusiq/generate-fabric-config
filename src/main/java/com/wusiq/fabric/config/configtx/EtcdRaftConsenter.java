package com.wusiq.fabric.config.configtx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Consenter entity of configtx.yaml config.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EtcdRaftConsenter {
    private String host;
    private Integer port;
    private String clientTLSCert;
    private String serverTLSCert;
}
