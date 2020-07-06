package com.wusiq.fabric.enums;
/**
 * Orderer type.
 */
public enum OrdererTypeEnum {
    SOLO("solo"),
    KAFKA("kafka"),
    ETCDRAFT("etcdraft");

    String value;

    OrdererTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
