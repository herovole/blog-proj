#!/bin/bash
# verify_containers.sh

set -e  # Stop on error

SCRIPT_DIR=$(cd $(dirname $0) && pwd)
source $SCRIPT_DIR/deploy_config.log

echo verifying backend container running
# Verify backend container is running
sudo docker ps | grep $DOCKER_BACKEND_CONTAINER

echo verifying frontend container running
# Verify frontend container is running
sudo docker ps | grep $DOCKER_FRONTEND_CONTAINER
