# Copyright IBM Corp. All Rights Reserved.
#
# SPDX-License-Identifier: Apache-2.0
#
OrdererOrgs:
[# th:each="ordererOrg : ${crypto.ordererOrgs}"]
  - Name: [(${ordererOrg.name})]
    Domain: [(${ordererOrg.domain})]
    EnableNodeOUs: [(${ordererOrg.enableNodeOUs})]
    Specs:
    [# th:each="hostName : ${ordererOrg.hostNames}"]
      - Hostname: [(${hostName})]
    [/]
[/]

PeerOrgs:
[# th:each="peerOrg : ${crypto.peerOrgs}"]
  - Name: [(${peerOrg.name})]
    Domain: [(${peerOrg.domain})]
    EnableNodeOUs: [(${peerOrg.enableNodeOUs})]
    Template:
      Count: [(${peerOrg.peerCount})]
    Users:
      Count: [(${peerOrg.userCount})]
[/]