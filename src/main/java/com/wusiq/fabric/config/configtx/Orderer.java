package com.wusiq.fabric.config.configtx;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * Orderer entity of configtx.yaml config.
 */
@Data
@Accessors(chain= true)
public class Orderer {
    private String ordererType;
    private List<AddressBak> addressBaks;
    private List<Org> orgs;
    private Map<String, Boolean> capabilities;
    private Map<String, List<AddressBak>> Kafka;
    private Map<String, List<EtcdRaftConsenterBak>> etcdRaft;
}