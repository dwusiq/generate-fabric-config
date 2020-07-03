package com.wusiq.fabric;

import com.wusiq.fabric.enums.PolicyAuthorityEnum;
import com.wusiq.fabric.enums.PolicyTypeEnum;

public class Constant {


    // PolicyAuthority
    public static final String POLICY_AUTHORITY_READERS = PolicyAuthorityEnum.READERS.getValue();
    public static final String POLICY_AUTHORITY_WRITERS = PolicyAuthorityEnum.WRITERS.getValue();
    public static final String POLICY_AUTHORITY_ADMINS = PolicyAuthorityEnum.ADMINS.getValue();
    public static final String POLICY_AUTHORITY_BLOCK_VALIDATION = PolicyAuthorityEnum.BLOCK_VALIDATION.getValue();


    // PolicyType
    public static final String POLICY_TYPE_SIGNATURE = PolicyTypeEnum.SIGNATURE.getValue();
    public static final String POLICY_TYPE_IMPLICIT_META = PolicyTypeEnum.IMPLICIT_META.getValue();

    //RULE:SIGNATURE
    public static final String POLICY_RULE_OR_ORG_MEMBER = "OR('OrdererMSP.member')";
    public static final String POLICY_RULE_OR_ADMIN_PEER_CLIENT = "OR('Org2MSP.admin', 'Org2MSP.peer', 'Org2MSP.client')";
    public static final String POLICY_RULE_OR_ADMIN_CLIENT = "OR('Org2MSP.admin', 'Org2MSP.client')";
    public static final String POLICY_RULE_OR_ADMIN = "OR(Org2MSP.admin)";

    //RULE:IMPLICIT_META
    public static final String POLICY_RULE_IMPLICIT_META_ANY_READERS = "ANY Readers";
    public static final String POLICY_RULE_IMPLICIT_META_ANY_WRITERS = "ANY Writers";
    public static final String POLICY_RULE_IMPLICIT_META_MAJORITY_ADMINS = "MAJORITY Admins";


}