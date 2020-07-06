package com.wusiq.fabric;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.wusiq.fabric.config.configx.*;
import com.wusiq.fabric.config.configx.factory.OrdererGenesisFactory;
import com.wusiq.fabric.config.configx.factory.PolicyFactory;
import com.wusiq.fabric.enums.OrdererTypeEnum;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Service
public class YamlService {

    public void writeConfigx() throws IOException {
        Map<String, Object> configxYaml = YamlService.getConfigx();

        String yamlPath = "./configs/configx.yaml";
        YamlService.writeYaml(configxYaml, yamlPath);
    }

    public static void writeYaml(Map<String, Object> channelConfigYaml, String filepath) throws IOException {
        File file = new File(filepath);
        // 创建文件
        file.createNewFile();
        // creates a FileWriter Object
        FileWriter writer = new FileWriter(file);
        String jsonAsYaml = new YAMLMapper().writeValueAsString(channelConfigYaml);

        writer.write(jsonAsYaml);

        //写入到文件中
        writer.flush();
        writer.close();
    }

    public static List<Organization> getOrgConfig() {
        Organization org1 = new Organization();
        String orgName = "Org1MSP";
        String anchorPeersHost = "peer0.org1.example.com";
        int anchorPeersPort = 7051;
        String orgId = "Org1MSP";
        String mSPDir = "crypto-config/peerOrganizations/org1.example.com/msp";
        org1.setName(orgName);
        org1.setId(orgId);
        org1.setMspDir(mSPDir);
        org1.setPolicies(PolicyFactory.getDefaultOrganization(orgName));
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
        org2.setPolicies(PolicyFactory.getDefaultOrganization(orgName));
        org2.setAnchorPeers(Arrays.asList(new Address(anchorPeersHost, anchorPeersPort)));

        return Arrays.asList(org1, org2);
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

    /**
     * get OrdererGenesis config.
     *
     * @return
     */
    public static OrdererGenesis getOrdererGenesis(List<Organization> organizationList) {
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
        List<Organization> organizationList = YamlService.getOrgConfig();

        Profiles profiles = new Profiles();
        profiles.setSoloOrdererGenesis(OrdererGenesisFactory.getOrdererGenesisByType(OrdererTypeEnum.SOLO,organizationList));
        profiles.setChannelConfig(YamlService.getChannel(organizationList));
        return profiles;
    }


    public static Map<String, Object> getConfigx() {
        Map<String, Object> channelConfigYaml = new LinkedHashMap<>();
        channelConfigYaml.put("Profiles", YamlService.getProfiles());

        return channelConfigYaml;
    }

    public static void main(String args[]) {
        YamlService yamlService = new YamlService();
        try {
            yamlService.writeConfigx();
            // yamlService.readYaml();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
