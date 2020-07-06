package com.wusiq.fabric.enums;

/**
 * Policy Authority.
 */
public enum PolicyAuthorityEnum {
    READERS("Readers"),
    WRITERS("Writers"),
    ADMINS("Admins"),
    BLOCK_VALIDATION("BlockValidation");

    String value;

    PolicyAuthorityEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}