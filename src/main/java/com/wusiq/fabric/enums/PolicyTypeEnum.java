package com.wusiq.fabric.enums;

/**
 * policy type.
 */
public enum PolicyTypeEnum {
    UNKNOWN("UNKNOWN"),
    SIGNATURE("Signature"),
    MSP("MSP"),
    IMPLICIT_META("ImplicitMeta");

    String value;

    PolicyTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
