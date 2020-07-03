package com.wusiq.fabric;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.wusiq.fabric.config.configx.SimpleOrgConfig;
import com.wusiq.fabric.config.configx.SimplePolicy;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.wusiq.fabric.Constant.*;

@Service
public class YamlService {
    ThreadLocal<Yaml> threadLocal = ThreadLocal.withInitial(() -> new Yaml());

    public void writeYaml() throws IOException {
//        Policy policies = new Policy();
//        policies.setReaders(Constant.POLICY_TYPE_SIGNATURE, Constant.POLICY_RULE_OR_ORG_MEMBER);
//        policies.setWriters(Constant.POLICY_TYPE_SIGNATURE, Constant.POLICY_RULE_OR_ORG_MEMBER);
//        policies.setAdmins(Constant.POLICY_TYPE_SIGNATURE, Constant.POLICY_RULE_OR_ADMIN);


        Map<String, SimplePolicy> policies = new LinkedHashMap<>();
        policies.put(POLICY_AUTHORITY_READERS, SimplePolicy.init(POLICY_TYPE_SIGNATURE, POLICY_RULE_OR_ORG_MEMBER));
        policies.put(POLICY_AUTHORITY_WRITERS, SimplePolicy.init(POLICY_TYPE_SIGNATURE, POLICY_RULE_OR_ORG_MEMBER));
        policies.put(POLICY_AUTHORITY_ADMINS, SimplePolicy.init(POLICY_TYPE_SIGNATURE, POLICY_RULE_OR_ADMIN));


        SimpleOrgConfig ordererSimpleOrgConfig = new SimpleOrgConfig();
        ordererSimpleOrgConfig.setName("OrdererOrg");
        ordererSimpleOrgConfig.setID("OrdererMSP");
        ordererSimpleOrgConfig.setMSPDir("crypto-config/ordererOrganizations/example.com/msp");
        ordererSimpleOrgConfig.setPolicies(policies);

        Yaml yaml = threadLocal.get();

        ObjectMapper objectMapper = new ObjectMapper();
        String prettyJSONString = objectMapper.writeValueAsString(ordererSimpleOrgConfig);
        // String prettyJSONString = JacksonUtils.objToString(OrdererOrg);
        // mapping
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) yaml.load(prettyJSONString);
        // convert to yaml string (yaml formatted string)

        File file = new File("./configs/configx.yaml");
        // 创建文件
        file.createNewFile();
        // creates a FileWriter Object
        FileWriter writer = new FileWriter(file);
        //   yaml.dump(prettyJSONString, writer);

        // String str = yaml.dump(map);

        String jsonAsYaml = new YAMLMapper().writeValueAsString(ordererSimpleOrgConfig);

        //用snakeyaml的dump方法将map类解析成yaml内容
        writer.write(jsonAsYaml);
        //写入到文件中
        writer.flush();
        writer.close();

    }


    public void readYaml() throws FileNotFoundException {
        //初始化Yaml解析器
        Yaml yaml = new Yaml();
        File f = new File("./configs/configx.yaml");
        //读入文件
        Object result = yaml.load(new FileInputStream(f));
        System.out.println(result.getClass());
        System.out.println(result);
    }


    public String asYaml(String jsonString) throws JsonProcessingException, IOException {
        // parse JSON
        JsonNode jsonNodeTree = new ObjectMapper().readTree(jsonString);
        // save it as YAML
        String jsonAsYaml = new YAMLMapper().writeValueAsString(jsonNodeTree);
        return jsonAsYaml;
    }


    public static void main(String args[]) {
        YamlService yamlService = new YamlService();
        try {
            yamlService.writeYaml();
            // yamlService.readYaml();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
