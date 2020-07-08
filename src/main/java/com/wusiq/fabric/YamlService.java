package com.wusiq.fabric;

import com.wusiq.fabric.config.configtx.Address;
import com.wusiq.fabric.config.configtx.ConfigtxYaml;
import com.wusiq.fabric.config.configtx.EtcdRaftConsenter;
import com.wusiq.fabric.config.configtx.Organization;
import com.wusiq.fabric.config.container.CaYaml;
import com.wusiq.fabric.config.container.CliYaml;
import com.wusiq.fabric.config.container.OrdererYaml;
import com.wusiq.fabric.config.container.PeerYaml;
import com.wusiq.fabric.config.crypto.CryptoYaml;
import com.wusiq.fabric.config.crypto.OrdererOrg;
import com.wusiq.fabric.config.crypto.PeerOrg;
import com.wusiq.fabric.enums.OrdererTypeEnum;
import com.wusiq.fabric.enums.OrgTypeEnum;
import com.wusiq.fabric.tools.ThymeleafUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class YamlService {


    public void writeCryptoConfig() throws IOException {
        //init OrdererOrg
        OrdererOrg ordererOrg = new OrdererOrg();
        ordererOrg.setName("Orderer");
        ordererOrg.setDomain("example.com");
        ordererOrg.setEnableNodeOUs(true);
        ordererOrg.setHostNames(Arrays.asList("orderer", "orderer2"));


        PeerOrg org1 = new PeerOrg();
        org1.setName("Org1");
        org1.setDomain("org1.example.com");
        org1.setPeerCount(2);
        org1.setUserCount(2);
        org1.setEnableNodeOUs(true);


        PeerOrg org2 = new PeerOrg();
        org2.setName("Org2");
        org2.setDomain("org2.example.com");
        org2.setPeerCount(1);
        org2.setUserCount(1);
        org2.setEnableNodeOUs(true);

        //init Crypto
        CryptoYaml cryptoYaml = new CryptoYaml();
        cryptoYaml.setOrdererOrgs(Arrays.asList(ordererOrg));
        cryptoYaml.setPeerOrgs(Arrays.asList(org1, org2));

        Path yamlPath = Paths.get("./configs");
        ThymeleafUtil.sampleCryptoConfig(yamlPath, cryptoYaml);
    }


    public void writeConfigtxYaml(OrdererTypeEnum ordererType) throws IOException {
        //init org list
        Organization ordererOrg = new Organization();
        ordererOrg.setName("OrdererOrg");
        ordererOrg.setMspId("OrdererMSP");
        ordererOrg.setOrgType(OrgTypeEnum.ORDERER.getValue());
        ordererOrg.setMspDir("crypto-config/ordererOrganizations/example.com/msp");

        Organization org1 = new Organization();
        org1.setName("Org1");
        org1.setMspId("Org1MSP");
        org1.setOrgType(OrgTypeEnum.PEER.getValue());
        org1.setMspDir("crypto-config/peerOrganizations/org1.example.com/msp");
        org1.setAnchorPeers(Arrays.asList(new Address("peer0.org1.example.com", 7051)));

        Organization org2 = new Organization();
        org2.setName("Org2");
        org2.setMspId("Org2MSP");
        org2.setOrgType(OrgTypeEnum.PEER.getValue());
        org2.setMspDir("crypto-config/peerOrganizations/org2.example.com/msp");
        org2.setAnchorPeers(Arrays.asList(new Address("peer0.org2.example.com", 8051)));

        //init configtxYaml
        ConfigtxYaml configtxYaml = new ConfigtxYaml();
        configtxYaml.setOrganizations(Arrays.asList(ordererOrg, org1, org2));
        configtxYaml.setOrdererType(ordererType.getValue());
        configtxYaml.setCapabilityVersion("V1_4_6");


        //init kafka or etcdraft
        if (OrdererTypeEnum.KAFKA == ordererType) {
            List<Address> brokers = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                Address broker = new Address();
                broker.setHost(i + "kafka.example.com");
                broker.setPort(9092);
                brokers.add(broker);
            }
            configtxYaml.setBrokers(brokers);
        } else if (OrdererTypeEnum.ETCDRAFT == ordererType) {
            List<EtcdRaftConsenter> consenters = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                EtcdRaftConsenter consenter = new EtcdRaftConsenter();
                consenter.setHost(i + "orderer.example.com");
                consenter.setPort(7050);
                consenter.setClientTLSCert(i + "crypto-config/ordererOrganizations/example.com/orderers/orderer.example.com/tls/server.crt");
                consenter.setServerTLSCert(i + "crypto-config/ordererOrganizations/example.com/orderers/orderer.example.com/tls/server.crt");
                consenters.add(consenter);
            }
            configtxYaml.setConsenters(consenters);
        }

        Path yamlPath = Paths.get("./configs");
        ThymeleafUtil.sampleConfigtxConfig(yamlPath, configtxYaml);
    }


    public void writeSampleCa() throws IOException {
        CaYaml cayaml = new CaYaml();
        cayaml.setServiceHost("ca.org1");
        cayaml.setContainerName("kdfa23sda-ca.org1.example");
        cayaml.setImageTag("1.4.6");
        cayaml.setTlsEnabled(true);
        cayaml.setCaPort(7050);
        cayaml.setCaCertFileName("ca.org1.example.com-cert.pem");
        cayaml.setCaPrivateKeyFileName("abasdafsdf.pk");
        cayaml.setCaCryptoPath("./crypto-config/peerOrganizations/org1.example.com/ca/");


        Path yamlPath = Paths.get("./configs");
        ThymeleafUtil.sampleCaConfig(yamlPath, cayaml);
    }


    public void writeSampleOrderer() throws IOException {
        OrdererYaml orderer = new OrdererYaml();
        orderer.setServiceHost("orderer.example.com");
        orderer.setExtraHosts(Arrays.asList("\"peer0.org1.example.com:192.168.8.10\"", "\"peer1.org1.example.com:192.168.8.11\""));
        orderer.setContainerName("kdfa23sda-orderer.example.com");
        orderer.setImageTag("1.4.6");
        orderer.setTlsEnabled(true);
        orderer.setMspId("Org1MSP");
        orderer.setMspPath("./crypto-config/ordererOrganizations/example.com/orderers/orderer.example.com/msp");
        orderer.setTlsPath("./crypto-config/ordererOrganizations/example.com/orderers/orderer.example.com/tls/");
        orderer.setOrdererPort(7050);


        Path yamlPath = Paths.get("./configs");
        ThymeleafUtil.sampleOrdererConfig(yamlPath, orderer);
    }


    public void writeSamplePeer() throws IOException {
        PeerYaml peerYaml = new PeerYaml();
        peerYaml.setServiceHost("peer0.org1.example.com");
        peerYaml.setExtraHosts(Arrays.asList("\"orderer.example.com:192.168.8.10\"", "\"peer1.org1.example.com:192.168.8.11\""));
        peerYaml.setContainerName("kdfa23sda-peer0.org1.example.com");
        peerYaml.setImageTag("1.4.6");
        peerYaml.setTlsEnabled(true);
        peerYaml.setDockerNetwork("firstnetwork_byfn");
        peerYaml.setMspId("Org1MSP");
        peerYaml.setMspPath("./crypto-config/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/msp");
        peerYaml.setTlsPath("./crypto-config/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls");
        peerYaml.setPeerPort(7051);
        peerYaml.setGossipBootstrap("peer1.org1.example.com:8051");
        peerYaml.setChaincodeListenPort(7052);


        CliYaml cliYaml = new CliYaml();
        cliYaml.setServiceHost("kdfa23sda-peer0-org1-cli");
        cliYaml.setExtraHosts(Arrays.asList("\"orderer.example.com:192.168.8.10\"", "\"peer1.org1.example.com:192.168.8.11\""));
        cliYaml.setContainerName("kdfa23sda-peer0-org1-cli");
        cliYaml.setImageTag("1.4.6");
        cliYaml.setTlsEnabled(true);
        cliYaml.setSystemChannelName("systemChannel");
        cliYaml.setDependOnHosts(Arrays.asList("orderer.example.com", "peer1.org1.example.com"));


        Path yamlPath = Paths.get("./configs");
        ThymeleafUtil.samplePeerConfig(yamlPath, peerYaml, cliYaml);
    }


    public static void main(String args[]) {
        YamlService yamlService = new YamlService();
        try {
            yamlService.writeCryptoConfig();
            yamlService.writeConfigtxYaml(OrdererTypeEnum.ETCDRAFT);
            yamlService.writeSampleCa();
            yamlService.writeSamplePeer();
            yamlService.writeSampleOrderer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
