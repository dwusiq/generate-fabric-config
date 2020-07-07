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
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
//        profiles.setEtcdRaftOrdererGenesis(OrdererGenesisFactory.getOrdererGenesisByType(OrdererTypeEnum.ETCDRAFT, peerOrgList, ordererOrgList));
//        profiles.setKafkaOrdererGenesis(OrdererGenesisFactory.getOrdererGenesisByType(OrdererTypeEnum.KAFKA, peerOrgList, ordererOrgList));
        profiles.setSoloOrdererGenesis(OrdererGenesisFactory.getOrdererGenesisByType(OrdererTypeEnum.SOLO, peerOrgList, ordererOrgList));

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
