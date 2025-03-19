#!/bin/bash

set -e  # Stop on error

SCRIPT_DIR=$(cd $(dirname $0) && pwd)
source $SCRIPT_DIR/deploy_config.log

# Check if the network exists
if sudo docker network ls --format \'{{.Name}}\' | grep -wq \"$DOCKER_NETWORK\"; then
    echo $(date) docker network ${DOCKER_NETWORK} already exists. Skipping creation. | tee -a $LOG_FILE
else
    echo $(date) Creating docker network ${DOCKER_NETWORK}. | tee -a $LOG_FILE
    sudo docker network create \"$DOCKER_NETWORK\"
fi

# Remove all containers tagged <none>.
# (Why isn't it performed in the last process? : To spare the second latest images on docker.)
sudo docker rmi $(sudo docker images -f "dangling=true" -q) --force

