package com.wusiq.fabric;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.wusiq.fabric.config.configx.*;
import com.wusiq.fabric.config.configx.factory.OrdererGenesisFactory;
import com.wusiq.fabric.config.configx.factory.PolicyFactory;
import com.wusiq.fabric.config.container.CliYaml;
import com.wusiq.fabric.config.container.OrdererYaml;
import com.wusiq.fabric.config.container.PeerYaml;
import com.wusiq.fabric.config.crypto.Crypto;
import com.wusiq.fabric.config.crypto.OrdererOrg;
import com.wusiq.fabric.config.crypto.PeerOrg;
import com.wusiq.fabric.enums.OrdererTypeEnum;
import com.wusiq.fabric.tools.ThymeleafUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class YamlService {


    public void writeSampleOrderer() throws IOException {
        OrdererYaml orderer = new OrdererYaml();
        orderer.setServiceHost("orderer.example.com");
        orderer.setExtraHosts(Arrays.asList("\"peer0.org1.example.com:192.168.8.10\"", "\"peer1.org1.example.com:192.168.8.11\""));
        orderer.setContainerName("kdfa23sda-orderer.example.com");
        orderer.setImageTag("1.4.6");
        orderer.setTlsEnabled(true);
        orderer.setDockerNetwork("firstnetwork_byfn");
//        orderer.setMspId("Org1MSP");
//        orderer.setMspPath("./crypto-config/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/msp");
//        orderer.setTlsPath("./crypto-config/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls");
//        orderer.setPeerPort(7051);
//        orderer.setGossipBootstrap("peer1.org1.example.com:8051");
//        orderer.setChaincodeListenPort(7052);



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
        cliYaml.setDockerNetwork("firstnetwork_byfn");
        cliYaml.setSystemChannelName("systemChannel");
        cliYaml.setDependOnHosts(Arrays.asList("orderer.example.com", "peer1.org1.example.com"));


        Path yamlPath = Paths.get("./configs");
        ThymeleafUtil.samplePeerConfig(yamlPath, peerYaml, cliYaml);
    }


    public void writeConfigx() throws IOException {
        Map<String, Object> configxYaml = YamlService.getConfigx();

        String yamlPath = "./configs/configx.yaml";
        YamlService.writeYaml(configxYaml, yamlPath);
    }


    public void writeCrypto() throws IOException {
        Crypto cryptoYaml = YamlService.getCrypto();

        String yamlPath = "./configs/crypto-config.yaml";
        YamlService.writeYaml(cryptoYaml, yamlPath);
    }

    public static void writeYaml(Object obj, String filepath) throws IOException {
        File file = new File(filepath);
        // 创建文件
        file.createNewFile();
        // creates a FileWriter Object
        FileWriter writer = new FileWriter(file);
        String jsonAsYaml = new YAMLMapper().writeValueAsString(obj);

        writer.write(jsonAsYaml);

        //写入到文件中
        writer.flush();
        writer.close();
    }

    public static List<Organization> getPeerOrgConfig() {
        Organization org1 = new Organization();
        String orgName = "Org1MSP";
        String anchorPeersHost = "peer0.org1.example.com";
        int anchorPeersPort = 7051;
        String orgId = "Org1MSP";
        String mSPDir = "crypto-config/peerOrganizations/org1.example.com/msp";
        org1.setName(orgName);
        org1.setId(orgId);
        org1.setMspDir(mSPDir);
        org1.setPolicies(PolicyFactory.getDefaultPeerOrgPolicy(orgName));
        org1.setAnchorPeers(Arrays.asList(new Address(anchorPeersHost, anchorPeersPort)));

        orgName = "Org2MSP";
        anchorPeersHost = "peer0.org2.example.com";
        anchorPeersPort = 7051;
        orgId = "Org2MSP";
        mSPDir = "crypto-config/peerOrganizations/org2.example.com/msp";
        Organization org2 = new Organization();
        org2.setName(orgName);
        org2.setId(orgId);
        org2.setMspDir(mSPDir);
        org2.setPolicies(PolicyFactory.getDefaultPeerOrgPolicy(orgName));
        org2.setAnchorPeers(Arrays.asList(new Address(anchorPeersHost, anchorPeersPort)));

        return Arrays.asList(org1, org2);
    }

    public static List<Organization> getOrdererOrgConfig() {
        Organization ordererOrg = new Organization();
        String orgName = "OrdererMSP";
        String orgId = "OrdererMSP";
        String mSPDir = "crypto-config/ordererOrganizations/example.com/msp";
        ordererOrg.setName(orgName);
        ordererOrg.setId(orgId);
        ordererOrg.setMspDir(mSPDir);
        ordererOrg.setPolicies(PolicyFactory.getDefaultOrdererOrgPolicy(orgName));

        return Arrays.asList(ordererOrg);
    }


    /**
     * get common  capabilities.
     *
     * @return
     */
    public static Map getCommCapabilities() {
        LinkedHashMap<String, Boolean> map = new LinkedHashMap();
        map.put("V1_4_2", true);
        return map;
    }


    public static Channel getChannel(List<Organization> organizationList) {

        //init application config
        Application application = new Application();
        application.setPolicies(PolicyFactory.getDefaultApplicationPolicy());
        application.setOrganizations(organizationList);
        application.setCapabilities(YamlService.getCommCapabilities());

        //init channel config
        Channel channel = new Channel();
        channel.setConsortium("SampleConsortium");
        channel.setPolicies(PolicyFactory.getDefaultChannelPolicy());
        channel.setCapabilities(YamlService.getCommCapabilities());
        channel.setApplication(application);

        return channel;
    }

    public static Profiles getProfiles() {
        //org list
        List<Organization> peerOrgList = YamlService.getPeerOrgConfig();
        List<Organization> ordererOrgList = YamlService.getOrdererOrgConfig();

        Profiles profiles = new Profiles();
        profiles.setApplicationChannel(YamlService.getChannel(peerOrgList));
//        profiles.setOrdererGenesis(OrdererGenesisFactory.getOrdererGenesisByType(OrdererTypeEnum.ETCDRAFT, peerOrgList, ordererOrgList));
//        profiles.setOrdererGenesis(OrdererGenesisFactory.getOrdererGenesisByType(OrdererTypeEnum.KAFKA, peerOrgList, ordererOrgList));
        profiles.setOrdererGenesis(OrdererGenesisFactory.getOrdererGenesisByType(OrdererTypeEnum.SOLO, peerOrgList, ordererOrgList));

        return profiles;
    }


    public static Map<String, Object> getConfigx() {
        Map<String, Object> channelConfigYaml = new LinkedHashMap<>();
        channelConfigYaml.put("Profiles", YamlService.getProfiles());

        return channelConfigYaml;
    }


    public static Crypto getCrypto() {
        //init OrdererOrg
        Map<String, String> specs = new LinkedHashMap<>();
        specs.put("Hostname", "orderer");

        OrdererOrg ordererOrg = new OrdererOrg();
        ordererOrg.setName("Orderer");
        ordererOrg.setDomain("example.com");
        ordererOrg.setEnableNodeOUs(true);
        ordererOrg.setSpecs(Arrays.asList(specs));


        //init PeerOrg
        List<PeerOrg> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Map<String, Integer> template = new LinkedHashMap<>();
            template.put("Count", 2);

            PeerOrg peerOrg = new PeerOrg();
            peerOrg.setName(String.format("Org%1s", i));
            peerOrg.setDomain(String.format("org%1s.example.com", i));
            peerOrg.setEnableNodeOUs(true);
            peerOrg.setTemplate(template);
            list.add(peerOrg);
        }

        Crypto crypto = new Crypto();
        crypto.setOrdererOrgs(Arrays.asList(ordererOrg));
        crypto.setPeerOrgs(list);

        return crypto;
    }


    public static void main(String args[]) {
        YamlService yamlService = new YamlService();
        try {
//            yamlService.writeConfigx();
//            yamlService.writeCrypto();
            yamlService.writeSamplePeer();
            yamlService.writeSampleOrderer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
