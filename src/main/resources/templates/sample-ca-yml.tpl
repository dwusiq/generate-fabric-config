# Copyright IBM Corp. All Rights Reserved.
#
# SPDX-License-Identifier: Apache-2.0
#

version: '2'

networks:
  byfn:

services:
  [(${caYaml.serviceHost})]:
    image: hyperledger/fabric-ca:[(${caYaml.imageTag})]
    container_name: [(${caYaml.containerName})]
    environment:
      - FABRIC_CA_HOME=/etc/hyperledger/fabric-ca-server
      - FABRIC_CA_SERVER_CA_NAME=[(${caYaml.serviceHost})]
      - FABRIC_CA_SERVER_TLS_ENABLED=[(${caYaml.tlsEnabled})]
      - FABRIC_CA_SERVER_TLS_CERTFILE=/etc/hyperledger/fabric-ca-server-config/[(${caYaml.caCertFileName})]
      - FABRIC_CA_SERVER_TLS_KEYFILE=/etc/hyperledger/fabric-ca-server-config/[(${caYaml.caPrivateKeyFileName})]
      - FABRIC_CA_SERVER_PORT=[(${caYaml.caPort})]
    ports:
      - "[(${caYaml.caPort})]:[(${caYaml.caPort})]"
    command: sh -c 'fabric-ca-server start --ca.certfile /etc/hyperledger/fabric-ca-server-config/[(${caYaml.caCertFileName})] --ca.keyfile /etc/hyperledger/fabric-ca-server-config/[(${caYaml.caPrivateKeyFileName})] -b admin:adminpw -d'
    volumes:
      - [(${caYaml.caCryptoPath})]:/etc/hyperledger/fabric-ca-server-config
    networks:
      - byfn