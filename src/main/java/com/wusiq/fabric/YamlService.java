package com.wusiq.fabric;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.wusiq.fabric.config.configx.Application;
import com.wusiq.fabric.config.configx.Channel;
import com.wusiq.fabric.config.configx.Node;
import com.wusiq.fabric.config.configx.Organization;
import com.wusiq.fabric.config.configx.factory.PolicyFactory;
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

        //init application config
        Application application = new Application();
        application.setPolicies(PolicyFactory.getDefaultApplicationPolicy());
        application.setOrganizations(YamlService.getOrgConfig());
        application.setCapabilities(YamlService.getCommCapabilities());

        //init channel config
        Channel channel = new Channel();
        channel.setConsortium("SampleConsortium");
        channel.setPolicies(PolicyFactory.getDefaultChannelPolicy());
        channel.setCapabilities(YamlService.getCommCapabilities());
        channel.setApplication(application);


        Map<String, Channel> channelConfigYaml = new LinkedHashMap<>();
        channelConfigYaml.put("TwoOrgsChannel", channel);

        String yamlPath = "./configs/configx.yaml";
        YamlService.writeYaml(channelConfigYaml, yamlPath);
    }

    public static void writeYaml(Map<String, Channel> channelConfigYaml, String filepath) throws IOException {
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
        org1.setAnchorPeers(Arrays.asList(new Node(anchorPeersHost, anchorPeersPort)));

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
        org2.setAnchorPeers(Arrays.asList(new Node(anchorPeersHost, anchorPeersPort)));

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
