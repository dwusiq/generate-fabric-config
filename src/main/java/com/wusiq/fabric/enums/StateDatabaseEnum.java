package com.wusiq.fabric.enums;
/**
 * db type.
 */
public enum StateDatabaseEnum {
    LEVELDB("leveldb"),
    COUCHDB("couchdb");

    String value;

    StateDatabaseEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
