#!/bin/bash

SYS_CHANNEL="systemchannel"
CHANNEL_NAME="mychannel"
ORDERER_CONSENSUS_TYPE=""
ORGS_STR=""



help() {
    echo $1
    cat << EOF
Usage:
    -s <name of system channel>    Default systemchannel
    -c <name of new channel>     Default mychannel
    -o <consensus-type> - the consensus-type of the ordering service: solo, or etcdraft
    -O <names of org,example "Org1,Org2">
    -h <help>
EOF

exit 0
}


parse_params()
{
while getopts "s:c:o:O:h" option;do
    case $option in
    s) SYS_CHANNEL=$OPTARG
    ;;
    c) CHANNEL_NAME=$OPTARG
    ;;
    o) ORDERER_CONSENSUS_TYPE=$OPTARG
    ;;
    O) ORGS_STR=$OPTARG;;
    h) help;;
    esac
done
}



function generateChannelArtifacts() {
  which configtxgen
  if [ "$?" -ne 0 ]; then
    echo "configtxgen tool not found. exiting"
    exit 1
  fi

  if [ ! -d "channel-artifacts" ];then
       echo "mkdir dir:channel-artifacts"
       mkdir "channel-artifacts"
  fi

  echo "##########################################################"
  echo "#########  Generating Orderer Genesis block ##############"
  echo "##########################################################"
  # Note: For some unknown reason (at least for now) the block file can't be
  # named orderer.genesis.block or the orderer will fail to launch!
  echo "ORDERER_CONSENSUS_TYPE="${ORDERER_CONSENSUS_TYPE}
  set -x
  if [ "${ORDERER_CONSENSUS_TYPE}" == "solo" ]; then
    configtxgen -profile MultiOrgsOrdererGenesis -channelID ${SYS_CHANNEL} -outputBlock ./channel-artifacts/genesis.block
  elif [ "${ORDERER_CONSENSUS_TYPE}" == "etcdraft" ]; then
    configtxgen -profile SampleMultiNodeEtcdRaft -channelID ${SYS_CHANNEL} -outputBlock ./channel-artifacts/genesis.block
  fi
  res=$?
  set +x
  if [ $res -ne 0 ]; then
    echo "Failed to generate orderer genesis block..."
    exit 1
  fi


  echo
  echo "#################################################################"
  echo "### Generating channel configuration transaction '${CHANNEL_NAME}_channel.tx' ###"
  echo "#################################################################"
  set -x
  configtxgen -profile MultiOrgsChannel -outputCreateChannelTx ./channel-artifacts/${CHANNEL_NAME}_channel.tx -channelID ${CHANNEL_NAME}
  res=$?
  set +x
  if [ $res -ne 0 ]; then
    echo "Failed to generate channel configuration transaction..."
    exit 1
  fi


  ORG_ARR=(${ORGS_STR//,/ })
  for ORG in ${ORG_ARR[@]};
  do
    echo
    echo "#################################################################"
    echo "#######    Generating anchor peer update for ${ORG}   ##########"
    echo "#################################################################"
    set -x
    configtxgen -profile MultiOrgsChannel -outputAnchorPeersUpdate ./channel-artifacts/${CHANNEL_NAME}_${ORG}_anchors.tx -channelID ${CHANNEL_NAME} -asOrg ${ORG}
    res=$?
    set +x
    if [ $res -ne 0 ]; then
      echo "Failed to generate anchor peer update for  ${ORG}..."
      exit 1
    fi
  done
  echo
}



parse_params $@
if [ "${ORGS_STR}" ==  "" ];then
    echo "please input param:MSPS"
    exit 1;
fi

generateChannelArtifacts
