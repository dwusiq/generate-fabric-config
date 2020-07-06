package com.wusiq.fabric.enums;
/**
 * Orderer type.
 */
public enum OrdererTypeEnum {
    SOLO("solo"),
    KAFKA("kafka"),
    ETCDRAFT("EtcdRaft");

    String value;

    OrdererTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
