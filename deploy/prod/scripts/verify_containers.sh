#!/bin/bash
# verify_containers.sh

set -e  # Stop on error

SCRIPT_DIR=$(cd $(dirname $0) && pwd)
source $SCRIPT_DIR/deploy_config.log

echo verifying backend container running
# Verify backend container is running
if sudo docker ps | grep $DOCKER_BACKEND_CONTAINER; then
    echo $(date) "${DOCKER_BACKEND_CONTAINER} deployed" | tee -a $LOG_FILE
else
    echo $(date) "${DOCKER_BACKEND_CONTAINER} failed to get deployed" | tee -a $LOG_FILE
    exit 1
fi

# Verify frontend container is running
if sudo docker ps | grep $DOCKER_FRONTEND_CONTAINER; then
    echo $(date) "${DOCKER_FRONTEND_CONTAINER} deployed" | tee -a $LOG_FILE
else
    echo $(date) "${DOCKER_FRONTEND_CONTAINER} failed to get deployed" | tee -a $LOG_FILE
    exit 1
fi

echo $(date) "Deployment successful." | tee -a $LOG_FILE
