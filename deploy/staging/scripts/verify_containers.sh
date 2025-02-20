#!/bin/bash
# verify_containers.sh

SCRIPT_DIR=$(cd $(dirname $0) && pwd)
source $SCRIPT_DIR/deploy_config.log

# Verify backend container is running
sudo docker ps | grep $DOCKER_BACKEND_CONTAINER

# Verify frontend container is running
sudo docker ps | grep $DOCKER_FRONTEND_CONTAINER
