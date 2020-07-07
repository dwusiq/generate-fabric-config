package com.wusiq.fabric.config.configx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Consenter entity of configx.yaml config.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EtcdRaftConsenter {
    @JsonProperty(value = "Host")
    private String host;
    @JsonProperty(value = "Port")
    private Integer port;
    @JsonProperty(value = "ClientTLSCert")
    private String clientTLSCert;
    @JsonProperty(value = "ServerTLSCert")
    private String serverTLSCert;
}
