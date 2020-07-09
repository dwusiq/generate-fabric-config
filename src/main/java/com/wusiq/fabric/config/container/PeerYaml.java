package com.wusiq.fabric.config.container;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PeerYaml extends BaseFabric {
    private Integer peerPort;
    private String mspId;
    private String mspPath;
    private String tlsPath;
    private String dockerNetwork;
    private String gossipBootstrap;
    private Integer chaincodeListenPort;
    private String stateDb;
    private String couchDbServiceName;
    private String couchDbContainerName;
    private String couchDbUser;
    private String couchDbPassword;
    private Integer couchDbPort;

}
