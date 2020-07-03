package com.wusiq.fabric.config.configx;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * policies of configx.yaml. example:
 * Policies:
 *   Readers:
 *     Type: Signature
 *     Rule: "OR('Org2MSP.admin', 'Org2MSP.peer', 'Org2MSP.client')"
 *   Writers:
 *     Type: Signature
 *     Rule: "OR('Org2MSP.admin', 'Org2MSP.client')"
 *   Admins:
 *     Type: Signature
 *     Rule: "OR('Org2MSP.admin')"
 *   BlockValidation:
 *     Type: ImplicitMeta
 *     Rule: "ANY Writers"
 */
public class Policy {
//    @JsonProperty(value = "Readers")
//    public SimplePolicy readers;
//    @JsonProperty(value = "Writers")
//    public SimplePolicy writers;
//    @JsonProperty(value = "Admins")
//    public SimplePolicy admins;
//    @JsonProperty(value = "BlockValidation")
//    public SimplePolicy blockValidation;
//
//    public void setReaders(String Type,String Rule){
//        this.readers = new SimplePolicy(Type,Rule);
//    }
//
//    public void setWriters(String Type,String Rule){
//        this.writers = new SimplePolicy(Type,Rule);
//    }
//
//    public void setAdmins(String Type,String Rule){
//        this.admins = new SimplePolicy(Type,Rule);
//    }
//
//    public void setBlockValidation(String Type,String Rule){
//        this.blockValidation = new SimplePolicy(Type,Rule);
//    }
//
//    @Data
//    @NoArgsConstructor
//    class SimplePolicy {
//        public SimplePolicy(String Type,String Rule){
//            this.Type=Type;
//            this.Rule=Rule;
//        }
//        private String Type;
//        private String Rule;
//    }
}

