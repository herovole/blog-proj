#!/bin/bash
# build_and_run_backend.sh

set -e  # Stop on error

SCRIPT_DIR=$(cd $(dirname $0) && pwd)
source $SCRIPT_DIR/deploy_config.log
source $AWS_USER_EC2

echo $(date) terminating previous backend container $DOCKER_BACKEND_CONTAINER | tee -a $LOG_FILE

# Check if the container exists (running or stopped)
if sudo docker ps -a --format \'{{.Names}}\' | grep -wq $DOCKER_BACKEND_CONTAINER; then
    echo $(date) \"Stopping and removing container \'$DOCKER_BACKEND_CONTAINER\'...\" | tee -a $LOG_FILE
    sudo docker stop $DOCKER_BACKEND_CONTAINER
    sudo docker rm $DOCKER_BACKEND_CONTAINER
else
    echo $(date) \"Container \'$DOCKER_BACKEND_CONTAINER\' does not exist. Skipping removal.\" | tee -a $LOG_FILE
fi

if sudo docker images --format "{{.Repository}}" | grep -q ${AWS_ECR_PREFIX}/${DOCKER_BACKEND_IMAGE}; then
    echo $(date) "Previous version of the Docker image '${DOCKER_BACKEND_IMAGE}' exists. removing..." | tee -a $LOG_FILE
    sudo docker rmi $(sudo docker images ${AWS_ECR_PREFIX}/${DOCKER_BACKEND_IMAGE} -q) --force
else
    echo $(date) "Docker image '${DOCKER_BACKEND_IMAGE}' does not exist. Skipping removal." | tee -a $LOG_FILE
fi

# Pull the backend image from ECR
echo $(date) pulling backend image $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$DOCKER_BACKEND_IMAGE:latest | tee -a $LOG_FILE
sudo docker pull $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$DOCKER_BACKEND_IMAGE:latest

if sudo docker images | grep -q $DOCKER_BACKEND_IMAGE; then
    echo $(date) "Image pulled out ${DOCKER_BACKEND_IMAGE}." | tee -a $LOG_FILE
else
    echo $(date) "No image ${DOCKER_BACKEND_IMAGE}." | tee -a $LOG_FILE
    exit 1
fi
