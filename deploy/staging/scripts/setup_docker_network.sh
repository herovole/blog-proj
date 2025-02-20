#!/bin/bash

SCRIPT_DIR=$(cd $(dirname $0) && pwd)
source $SCRIPT_DIR/deploy_config.log

# Check if the network exists
if sudo docker network ls --format \'{{.Name}}\' | grep -wq \"$DOCKER_NETWORK\"; then
    echo \"Network \'$DOCKER_NETWORK\' already exists. Skipping creation.\"
else
    echo \"Creating Docker network \'$DOCKER_NETWORK\'...\"
    sudo docker network create \"$DOCKER_NETWORK\"
fi
