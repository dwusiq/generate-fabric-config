# Copyright IBM Corp. All Rights Reserved.
#
# SPDX-License-Identifier: Apache-2.0
#

version: '2'

volumes:
  [(${orderer.serviceHost})]:

networks:
  byfn:

services:
  [(${orderer.serviceHost})]:
    image: hyperledger/fabric-orderer:[(${orderer.imageTag})]
    container_name: [(${orderer.containerName})]
    volumes:
      - ./channel-artifacts/genesis.block:/var/hyperledger/orderer/orderer.genesis.block
      - [(${orderer.mspPath})]:/var/hyperledger/orderer/msp
      - [(${orderer.tlsPath})]:/var/hyperledger/orderer/tls
      - [(${orderer.serviceHost})]:/var/hyperledger/production/orderer
    environment:
      - FABRIC_LOGGING_SPEC=INFO
      - ORDERER_GENERAL_LISTENADDRESS=0.0.0.0
      - ORDERER_GENERAL_GENESISMETHOD=file
      - ORDERER_GENERAL_GENESISFILE=/var/hyperledger/orderer/orderer.genesis.block
      - ORDERER_GENERAL_LOCALMSPID=[(${orderer.mspId})]
      - ORDERER_GENERAL_LOCALMSPDIR=/var/hyperledger/orderer/msp
      # enabled TLS
      - ORDERER_GENERAL_TLS_ENABLED=[(${orderer.tlsEnabled})]
      - ORDERER_GENERAL_TLS_PRIVATEKEY=/var/hyperledger/orderer/tls/server.key
      - ORDERER_GENERAL_TLS_CERTIFICATE=/var/hyperledger/orderer/tls/server.crt
      - ORDERER_GENERAL_TLS_ROOTCAS=/var/hyperledger/orderer/tls/ca.crt
      - ORDERER_KAFKA_TOPIC_REPLICATIONFACTOR=1
      - ORDERER_KAFKA_VERBOSE=true
      - ORDERER_GENERAL_CLUSTER_CLIENTCERTIFICATE=/var/hyperledger/orderer/tls/server.crt
      - ORDERER_GENERAL_CLUSTER_CLIENTPRIVATEKEY=/var/hyperledger/orderer/tls/server.
      - ORDERER_GENERAL_CLUSTER_ROOTCAS=/var/hyperledger/orderer/tls/ca.crt

    working_dir: /opt/gopath/src/github.com/hyperledger/fabric
    command: orderer
    ports:
      - [(${orderer.ordererPort})]:[(${orderer.ordererPort})]
    networks:
      - byfn
    extra_hosts: [# th:each="ordererExtraHhost : ${orderer.extraHosts}"]
      - [(${ordererExtraHhost})]
      [/]