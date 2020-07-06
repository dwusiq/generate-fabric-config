package com.wusiq.fabric.config.configx.factory;

import com.wusiq.fabric.config.configx.Policy;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.wusiq.fabric.Constant.*;

/**
 * get config of policy.
 */
public class PolicyFactory {

    /**
     * get default config of channel policy.
     * @return
     */
    public static Map<String, Policy> getDefaultChannelPolicy() {
        Map<String, Policy> policies = new LinkedHashMap<>();
        policies.put(POLICY_AUTHORITY_READERS, Policy.init(POLICY_TYPE_IMPLICIT_META, POLICY_RULE_IMPLICIT_META_ANY_READERS));
        policies.put(POLICY_AUTHORITY_WRITERS, Policy.init(POLICY_TYPE_IMPLICIT_META, POLICY_RULE_IMPLICIT_META_ANY_WRITERS));
        policies.put(POLICY_AUTHORITY_ADMINS, Policy.init(POLICY_TYPE_IMPLICIT_META, POLICY_RULE_IMPLICIT_META_MAJORITY_ADMINS));
        return policies;
    }

    /**
     * get default config of application policy.
     * @return
     */
    public static Map<String, Policy> getDefaultApplicationPolicy() {
        Map<String, Policy> policies = new LinkedHashMap<>();
        policies.put(POLICY_AUTHORITY_READERS, Policy.init(POLICY_TYPE_IMPLICIT_META, POLICY_RULE_IMPLICIT_META_ANY_READERS));
        policies.put(POLICY_AUTHORITY_WRITERS, Policy.init(POLICY_TYPE_IMPLICIT_META, POLICY_RULE_IMPLICIT_META_ANY_WRITERS));
        policies.put(POLICY_AUTHORITY_ADMINS, Policy.init(POLICY_TYPE_IMPLICIT_META, POLICY_RULE_IMPLICIT_META_MAJORITY_ADMINS));
        return policies;
    }

    /**
     * get default config of organization policy.
     * @return
     */
    public static Map<String, Policy> getDefaultOrganization(String mapId) {
        Map<String, Policy> policies = new LinkedHashMap<>();
        policies.put(POLICY_AUTHORITY_READERS, Policy.init(POLICY_TYPE_SIGNATURE, POLICY_RULE_OR_ADMIN_PEER_CLIENT.replace("MspID",mapId)));
        policies.put(POLICY_AUTHORITY_WRITERS, Policy.init(POLICY_TYPE_SIGNATURE, POLICY_RULE_OR_ADMIN_CLIENT.replace("MspID",mapId)));
        policies.put(POLICY_AUTHORITY_ADMINS, Policy.init(POLICY_TYPE_SIGNATURE, POLICY_RULE_OR_ADMIN.replace("MspID",mapId)));
        return policies;
    }
}
