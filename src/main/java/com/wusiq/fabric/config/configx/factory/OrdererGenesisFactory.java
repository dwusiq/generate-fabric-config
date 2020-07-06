package com.wusiq.fabric.config.configx.factory;

import com.wusiq.fabric.YamlService;
import com.wusiq.fabric.config.configx.*;
import com.wusiq.fabric.enums.OrdererTypeEnum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * get orderer.
 */
public class OrdererGenesisFactory {

    public static OrdererGenesis getOrdererGenesisByType(OrdererTypeEnum ordererType, List<Organization> organizationList) {

        if (OrdererTypeEnum.KAFKA == ordererType) {
            return getKafkaOrdererGenesis(organizationList);
        }
        if (OrdererTypeEnum.ETCDRAFT == ordererType) {
            return getEtcdRaftOrdererGenesis(organizationList);
        }
        return getSoloOrdererGenesis(organizationList);
    }

    /**
     * get OrdererGenesis of Kafka.
     *
     * @return
     */
    private static OrdererGenesis getKafkaOrdererGenesis( List<Organization> organizationList) {
        return null;
    }

    /**
     * get OrdererGenesis of EtcdRaft.
     *
     * @return
     */
    private static OrdererGenesis getEtcdRaftOrdererGenesis( List<Organization> organizationList) {
        return null;
    }

    /**
     * get OrdererGenesis of solo.
     *
     * @return
     */
    private static OrdererGenesis getSoloOrdererGenesis( List<Organization> organizationList) {
        //init batchSize
        Map<String, Object> batchSize = new HashMap<>();
        batchSize.put("MaxMessageCount", 10);
        batchSize.put("AbsoluteMaxBytes", "99 MB");
        batchSize.put("PreferredMaxBytes", "512 KB");


        // init orderer
        Orderer orderer = new Orderer();
        orderer.setOrdererType(OrdererTypeEnum.SOLO.getValue());
        orderer.setAddresses(Arrays.asList(new Address("orderer.example.com", 7050)));
        orderer.setBatchTimeout("2s");
        orderer.setBatchSize(batchSize);
        orderer.setMaxChannels(0);
        orderer.setPolicies(PolicyFactory.getDefaultOrdererPolicy());
        orderer.setOrganizations(organizationList);
        orderer.setCapabilities(YamlService.getCommCapabilities());

        //init Consortium
        Map<String, Consortium> sampleConsortium = new HashMap<>();
        sampleConsortium.put("SampleConsortium", new Consortium(organizationList));


        //init OrdererGenesis config
        OrdererGenesis ordererGenesis = new OrdererGenesis();
        ordererGenesis.setOrderer(orderer);
        ordererGenesis.setPolicies(PolicyFactory.getDefaultOrdererGenesisPolicy());
        ordererGenesis.setCapabilities(YamlService.getCommCapabilities());
        ordererGenesis.setConsortiums(sampleConsortium);


        return ordererGenesis;
    }
}
