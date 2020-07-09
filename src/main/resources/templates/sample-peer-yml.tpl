# Copyright IBM Corp. All Rights Reserved.
#
# SPDX-License-Identifier: Apache-2.0
#

version: '2'

volumes:
  [(${peerYaml.serviceHost})]:

networks:
  byfn:

services:
[# th:if="${peerYaml.stateDb}=='couchdb'"]
  [(${peerYaml.couchDbServiceName})]:
    container_name: [(${peerYaml.couchDbContainerName})]
    image: hyperledger/fabric-couchdb
    environment:
      - COUCHDB_USER=[(${peerYaml.couchDbUser})]
      - COUCHDB_PASSWORD=[(${peerYaml.couchDbPassword})]
    volumes:
      - /var/hyperledger/[(${peerYaml.couchDbServiceName})]:/opt/couchdb/data
    ports:
      - "[(${peerYaml.couchDbPort})]:[(${peerYaml.couchDbPort})]"
    networks:
      - byfn
[/]

  [(${peerYaml.serviceHost})]:
    image: hyperledger/fabric-peer:[(${peerYaml.imageTag})]
    container_name: [(${peerYaml.containerName})]
    environment:
      - FABRIC_LOGGING_SPEC=INFO
      - CORE_VM_ENDPOINT=unix:///host/var/run/docker.sock
      - CORE_VM_DOCKER_HOSTCONFIG_NETWORKMODE=[(${peerYaml.dockerNetwork})]
      - CORE_PEER_ID=[(${peerYaml.serviceHost})]
      - CORE_PEER_ADDRESS=[(${peerYaml.serviceHost})]:[(${peerYaml.peerPort})]
      - CORE_PEER_LISTENADDRESS=0.0.0.0:[(${peerYaml.peerPort})]
      - CORE_PEER_CHAINCODEADDRESS=[(${peerYaml.serviceHost})]:[(${peerYaml.chaincodeListenPort})]
      - CORE_PEER_CHAINCODELISTENADDRESS=0.0.0.0:[(${peerYaml.chaincodeListenPort})]
      - CORE_PEER_GOSSIP_BOOTSTRAP=[(${peerYaml.gossipBootstrap})]
      - CORE_PEER_GOSSIP_EXTERNALENDPOINT=[(${peerYaml.serviceHost})]:[(${peerYaml.peerPort})]
      - CORE_PEER_LOCALMSPID=[(${peerYaml.mspId})]
      - CORE_PEER_TLS_ENABLED=[(${peerYaml.tlsEnabled})]
      - CORE_PEER_GOSSIP_USELEADERELECTION=true
      - CORE_PEER_GOSSIP_ORGLEADER=false
      - CORE_PEER_PROFILE_ENABLED=true
      - CORE_PEER_TLS_CERT_FILE=/etc/hyperledger/fabric/tls/server.crt
      - CORE_PEER_TLS_KEY_FILE=/etc/hyperledger/fabric/tls/server.key
      - CORE_PEER_TLS_ROOTCERT_FILE=/etc/hyperledger/fabric/tls/ca.crt
    [# th:if="${peerYaml.stateDb}=='couchdb'"]
      - CORE_LEDGER_STATE_STATEDATABASE=[(${peerYaml.couchDbServiceName})]
      - CORE_LEDGER_STATE_COUCHDBCONFIG_COUCHDBADDRESS=[(${peerYaml.couchDbServiceName})]:[(${peerYaml.couchDbPort})]
      - CORE_LEDGER_STATE_COUCHDBCONFIG_USERNAME=[(${peerYaml.couchDbUser})]
      - CORE_LEDGER_STATE_COUCHDBCONFIG_PASSWORD=[(${peerYaml.couchDbPassword})]
    [/]
    volumes:
      - /var/run/:/host/var/run/
      - [(${peerYaml.mspPath})]:/etc/hyperledger/fabric/msp
      - [(${peerYaml.tlsPath})]:/etc/hyperledger/fabric/tls
      - [(${peerYaml.serviceHost})]:/var/hyperledger/production
      - /var/hyperledger/[(${peerYaml.serviceHost})]:/var/hyperledger/production
    ports:
      - [(${peerYaml.peerPort})]:[(${peerYaml.peerPort})]
    working_dir: /opt/gopath/src/github.com/hyperledger/fabric/peer
    command: peer node start
  [# th:if="${peerYaml.stateDb}=='couchdb'"]
    depends_on:
      - [(${peerYaml.couchDbServiceName})]
  [/]
    networks:
      - byfn
    extra_hosts: [# th:each="peerExtraHhost : ${peerYaml.extraHosts}"]
      - [(${peerExtraHhost})]
      [/]


  [(${cliYaml.serviceHost})]:
    container_name: [(${cliYaml.containerName})]
    image: hyperledger/fabric-tools:[(${cliYaml.imageTag})]
    tty: true
    stdin_open: true
    environment:
      - SYS_CHANNEL=[(${cliYaml.systemChannelName})]
      - GOPATH=/opt/gopath
      - CORE_VM_ENDPOINT=unix:///host/var/run/docker.sock
      #- FABRIC_LOGGING_SPEC=DEBUG
      - FABRIC_LOGGING_SPEC=INFO
      - CORE_PEER_ID=[(${cliYaml.serviceHost})]
      - CORE_PEER_ADDRESS=[(${peerYaml.serviceHost})]:[(${peerYaml.peerPort})]
      - CORE_PEER_LOCALMSPID=[(${peerYaml.mspId})]
      - CORE_PEER_TLS_ENABLED=[(${peerYaml.tlsEnabled})]
      - CORE_PEER_TLS_CERT_FILE=/opt/gopath/src/github.com/hyperledger/fabric/peer/cryptoYaml/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/server.crt
      - CORE_PEER_TLS_KEY_FILE=/opt/gopath/src/github.com/hyperledger/fabric/peer/cryptoYaml/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/server.key
      - CORE_PEER_TLS_ROOTCERT_FILE=/opt/gopath/src/github.com/hyperledger/fabric/peer/cryptoYaml/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt
      - CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/cryptoYaml/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp
    working_dir: /opt/gopath/src/github.com/hyperledger/fabric/peer
    command: /bin/bash
    volumes:
      - /var/run/:/host/var/run/
      - ./chaincode/:/opt/gopath/src/github.com/chaincode
      - ./cryptoYaml-config:/opt/gopath/src/github.com/hyperledger/fabric/peer/cryptoYaml/
      - ./scripts:/opt/gopath/src/github.com/hyperledger/fabric/peer/scripts/
      - ./channel-artifacts:/opt/gopath/src/github.com/hyperledger/fabric/peer/channel-artifacts
    depends_on:[# th:each="dependHost : ${cliYaml.dependOnHosts}"]
      - [(${dependHost})]
      [/]
    networks:
      - byfn
    extra_hosts:[# th:each="cliExtraHost : ${cliYaml.extraHosts}"]
      - [(${cliExtraHost})]
      [/]