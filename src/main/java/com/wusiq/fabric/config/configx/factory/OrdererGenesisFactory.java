package com.wusiq.fabric.config.configx.factory;

import com.wusiq.fabric.YamlService;
import com.wusiq.fabric.config.configx.*;
import com.wusiq.fabric.enums.OrdererTypeEnum;

import java.util.*;

/**
 * get orderer.
 */
public class OrdererGenesisFactory {

    public static OrdererGenesis getOrdererGenesisByType(OrdererTypeEnum ordererType, List<Organization> peerOrgList, List<Organization> peerOrdererOrgList) {

        if (OrdererTypeEnum.KAFKA == ordererType) {
            return getKafkaOrdererGenesis(peerOrgList, peerOrdererOrgList);
        }
        if (OrdererTypeEnum.ETCDRAFT == ordererType) {
            return getEtcdRaftOrdererGenesis(peerOrgList, peerOrdererOrgList);
        }
        return getSoloOrdererGenesis(peerOrgList, peerOrdererOrgList);
    }

    /**
     * get OrdererGenesis of Kafka.
     *
     * @return
     */
    private static OrdererGenesis getKafkaOrdererGenesis(List<Organization> peerOrgList, List<Organization> peerOrdererOrgList) {
        //init kafka
        List<Address> addressList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Address address = new Address();
            address.setHost(i + "kafka.example.com");
            address.setPort(9092);
        }
        Map<String, List<Address>> kafka = new HashMap<>();
        kafka.put("Brokers", addressList);

        //init OrdererGenesis of kafka
        OrdererGenesis ordererGenesis = OrdererGenesisFactory.getDefaultOrdererGenesis(peerOrgList, peerOrdererOrgList);
        ordererGenesis.getOrderer().setOrdererType(OrdererTypeEnum.KAFKA.getValue()).setKafka(kafka);

        return ordererGenesis;
    }

    /**
     * get OrdererGenesis of EtcdRaft.
     *
     * @return
     */
    private static OrdererGenesis getEtcdRaftOrdererGenesis(List<Organization> peerOrgList, List<Organization> peerOrdererOrgList) {
        //init EtcdRaft
        List<EtcdRaftConsenter> consenterList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            EtcdRaftConsenter consenter = new EtcdRaftConsenter();
            consenter.setHost(i + "orderer.example.com");
            consenter.setPort(7050);
            consenter.setClientTLSCert(i + "crypto-config/ordererOrganizations/example.com/orderers/orderer.example.com/tls/server.crt");
            consenter.setServerTLSCert(i + "crypto-config/ordererOrganizations/example.com/orderers/orderer.example.com/tls/server.crt");
            consenterList.add(consenter);
        }
        Map<String, List<EtcdRaftConsenter>> etcdRaft = new HashMap<>();
        etcdRaft.put("EtcdRaft", consenterList);

        //init OrdererGenesis of EtcdRaft
        OrdererGenesis ordererGenesis = OrdererGenesisFactory.getDefaultOrdererGenesis(peerOrgList, peerOrdererOrgList);
        ordererGenesis.getOrderer().setOrdererType(OrdererTypeEnum.ETCDRAFT.getValue()).setEtcdRaft(etcdRaft);

        return ordererGenesis;
    }

    /**
     * get OrdererGenesis of solo.
     *
     * @return
     */
    private static OrdererGenesis getSoloOrdererGenesis(List<Organization> peerOrgList, List<Organization> peerOrdererOrgList) {
        return OrdererGenesisFactory.getDefaultOrdererGenesis(peerOrgList, peerOrdererOrgList);
    }


    /**
     * default orderer genesis config.
     *
     * @param peerOrgList
     * @param peerOrdererOrgList
     * @return
     */
    private static OrdererGenesis getDefaultOrdererGenesis(List<Organization> peerOrgList, List<Organization> peerOrdererOrgList) {
        //init batchSize
        Map<String, Object> batchSize = new HashMap<>();
        batchSize.put("MaxMessageCount", 10);
        batchSize.put("AbsoluteMaxBytes", "99 MB");
        batchSize.put("PreferredMaxBytes", "512 KB");

        //init kafka
        List<Address> addressList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Address address = new Address();
            address.setHost(i + "kafka.example.com");
            address.setPort(9092);
        }
        Map<String, List<Address>> kafka = new HashMap<>();
        kafka.put("Brokers", addressList);


        // init orderer
        Orderer orderer = new Orderer();
        orderer.setOrdererType(OrdererTypeEnum.SOLO.getValue());
        orderer.setAddresses(Arrays.asList(new Address("orderer.example.com", 7050)));
        orderer.setBatchTimeout("2s");
        orderer.setBatchSize(batchSize);
        orderer.setMaxChannels(0);
        orderer.setPolicies(PolicyFactory.getDefaultOrdererPolicy());
        orderer.setOrganizations(peerOrdererOrgList);
        orderer.setCapabilities(YamlService.getCommCapabilities());
//        orderer.setKafka(kafka);

        //init application config
        Application application = new Application();
        application.setPolicies(PolicyFactory.getDefaultApplicationPolicy());
        application.setOrganizations(peerOrdererOrgList);
        application.setCapabilities(YamlService.getCommCapabilities());

        //init Consortium
        Map<String, Consortium> sampleConsortium = new HashMap<>();
        sampleConsortium.put("SampleConsortium", new Consortium(peerOrgList));


        //init OrdererGenesis config
        OrdererGenesis ordererGenesis = new OrdererGenesis();
        ordererGenesis.setOrderer(orderer);
        ordererGenesis.setPolicies(PolicyFactory.getDefaultOrdererGenesisPolicy());
        ordererGenesis.setCapabilities(YamlService.getCommCapabilities());
        ordererGenesis.setApplication(application);
        ordererGenesis.setConsortiums(sampleConsortium);

        return ordererGenesis;
    }
}
