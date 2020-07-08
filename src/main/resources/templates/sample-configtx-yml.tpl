Organizations:
[# th:each="organization : ${configtxYaml.organizations}"]
    - &[(${organization.name})]
        Name: [(${organization.name})]
        ID: [(${organization.mspId})]
        MSPDir: [(${organization.mspDir})]
  [# th:if="${organization.orgType}=='orderer'"]
        Policies:
            Readers:
                Type: Signature
                Rule: "OR('[(${organization.mspId})].member')"
            Writers:
                Type: Signature
                Rule: "OR('[(${organization.mspId})].member')"
            Admins:
                Type: Signature
                Rule: "OR('[(${organization.mspId})].admin')"
  [/]
  [# th:if="${organization.orgType}=='peer'"]
        Policies:
            Readers:
                Type: Signature
                Rule: "OR('[(${organization.mspId})].admin', '[(${organization.mspId})].peer', '[(${organization.mspId})].client')"
            Writers:
                Type: Signature
                Rule: "OR('[(${organization.mspId})].admin', '[(${organization.mspId})].client')"
            Admins:
                Type: Signature
                Rule: "OR('[(${organization.mspId})].admin')"
        AnchorPeers:
          [# th:each="anchorPeer : ${organization.anchorPeers}"]
            - Host: [(${anchorPeer.host})]
              Port: [(${anchorPeer.port})]
          [/]
  [/]
[/]

Capabilities:
    Channel: &ChannelCapabilities
        [(${configtxYaml.capabilityVersion})]: true
        V1_3: false
        V1_1: false
    Orderer: &OrdererCapabilities
        [(${configtxYaml.capabilityVersion})]: true
        V1_1: false
    Application: &ApplicationCapabilities
        [(${configtxYaml.capabilityVersion})]: true
        V1_3: false
        V1_2: false
        V1_1: false


Application: &ApplicationDefaults
    Organizations:
    Policies:
        Readers:
            Type: ImplicitMeta
            Rule: "ANY Readers"
        Writers:
            Type: ImplicitMeta
            Rule: "ANY Writers"
        Admins:
            Type: ImplicitMeta
            Rule: "MAJORITY Admins"
    Capabilities:
        <<: *ApplicationCapabilities
Orderer: &OrdererDefaults
    BatchTimeout: 2s
    BatchSize:
        MaxMessageCount: 10
        AbsoluteMaxBytes: 99 MB
        PreferredMaxBytes: 512 KB
    Policies:
        Readers:
            Type: ImplicitMeta
            Rule: "ANY Readers"
        Writers:
            Type: ImplicitMeta
            Rule: "ANY Writers"
        Admins:
            Type: ImplicitMeta
            Rule: "MAJORITY Admins"
        BlockValidation:
            Type: ImplicitMeta
            Rule: "ANY Writers"


Channel: &ChannelDefaults
    Policies:
        Readers:
            Type: ImplicitMeta
            Rule: "ANY Readers"
        Writers:
            Type: ImplicitMeta
            Rule: "ANY Writers"
        Admins:
            Type: ImplicitMeta
            Rule: "MAJORITY Admins"
    Capabilities:
        <<: *ChannelCapabilities


Profiles:
    MultiOrgsChannel:
        Consortium: SampleConsortium
        <<: *ChannelDefaults
        Application:
            <<: *ApplicationDefaults
            Organizations:
            [# th:each="organization : ${configtxYaml.organizations}"]
              [# th:if="${organization.orgType}=='peer'"]
                - *[(${organization.name})]
              [/]
            [/]
            Capabilities:
                <<: *ApplicationCapabilities

[# th:switch="${configtxYaml.ordererType}"]
  [# th:case="'solo'"]
    MultiOrgsOrdererGenesis:
        <<: *ChannelDefaults
        Orderer:
            <<: *OrdererDefaults
            OrdererType: solo
            Addresses:
                - orderer.example.com:7050
            Organizations:
                - *OrdererOrg
            Capabilities:
                <<: *OrdererCapabilities
        Consortiums:
            SampleConsortium:
                Organizations:
                [# th:each="organization : ${configtxYaml.organizations}"]
                   [# th:if="${organization.orgType}=='peer'"]
                     - *[(${organization.name})]
                   [/]
                [/]
  [/]

  [# th:case="'etcdraft'"]
    SampleMultiNodeEtcdRaft:
        <<: *ChannelDefaults
        Capabilities:
            <<: *ChannelCapabilities
        Orderer:
            <<: *OrdererDefaults
            OrdererType: etcdraft
            EtcdRaft:
               Consenters:
               [# th:each="consenter : ${configtxYaml.consenters}"]
               - Host: [(${consenter.host})]
                 Port: [(${consenter.port})]
                 ClientTLSCert: [(${consenter.clientTLSCert})]
                 ServerTLSCert: [(${consenter.serverTLSCert})]
               [/]
            Addresses:
            [# th:each="consenter : ${configtxYaml.consenters}"]
                - [(${consenter.host})]: [(${consenter.port})]
            [/]
            Organizations:
            - *OrdererOrg
            Capabilities:
                <<: *OrdererCapabilities
        Application:
            <<: *ApplicationDefaults
            Organizations:
            - <<: *OrdererOrg
        Consortiums:
            SampleConsortium:
                Organizations:
                [# th:each="organization : ${configtxYaml.organizations}"]
                  [# th:if="${organization.orgType}=='peer'"]
                    - *[(${organization.name})]
                  [/]
                [/]
  [/]

  [# th:case="'kafka'"]
    SampleKafka:
        <<: *ChannelDefaults
        Capabilities:
            <<: *ChannelCapabilities
        Orderer:
            <<: *OrdererDefaults
            OrdererType: kafka
            Kafka:
                Brokers:
                [# th:each="broker : ${configtxYaml.brokers}"]
                - [(${broker.host})]:[(${broker.port})]
                [/]
            Organizations:
            - *OrdererOrg
            Capabilities:
                <<: *OrdererCapabilities
        Application:
            <<: *ApplicationDefaults
            Organizations:
            - <<: *OrdererOrg
        Consortiums:
            SampleConsortium:
                Organizations:
                [# th:each="organization : ${configtxYaml.organizations}"]
                  [# th:if="${organization.orgType}=='peer'"]
                    - *[(${organization.name})]
                  [/]
                [/]
  [/]
[/]


