package com.wusiq.fabric.enums;
/**
 * organization type.
 */
public enum OrgTypeEnum {
    ORDERER("orderer"),
    PEER("peer");

    String value;

    OrgTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
