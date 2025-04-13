#!/bin/bash
# build_and_run_frontend.sh

set -e  # Stop on error

SCRIPT_DIR=$(cd $(dirname $0) && pwd)
source $SCRIPT_DIR/deploy_config.log
source $AWS_USER_EC2

echo $(date) terminating previous frontend container $DOCKER_FRONTEND_CONTAINER | tee -a $LOG_FILE

# Check if the container exists (running or stopped)
if sudo docker ps -a --format \'{{.Names}}\' | grep -wq $DOCKER_FRONTEND_CONTAINER; then
    echo $(date) \"Stopping and removing container \'$DOCKER_FRONTEND_CONTAINER\'...\" | tee -a $LOG_FILE
    sudo docker stop $DOCKER_FRONTEND_CONTAINER
    sudo docker rm $DOCKER_FRONTEND_CONTAINER
else
    echo $(date) \"Container \'$DOCKER_FRONTEND_CONTAINER\' does not exist. Skipping removal.\" | tee -a $LOG_FILE
fi

if sudo docker images --format "{{.Repository}}" | grep -q ${AWS_ECR_PREFIX}/${DOCKER_FRONTEND_IMAGE}; then
    echo $(date) "Previous version of the Docker image '${DOCKER_FRONTEND_IMAGE}' exists. removing..." | tee -a $LOG_FILE
    sudo docker rmi $(sudo docker images ${AWS_ECR_PREFIX}/${DOCKER_FRONTEND_IMAGE} -q) --force
else
    echo $(date) "Docker image '${DOCKER_FRONTEND_IMAGE}' does not exist. Skipping removal." | tee -a $LOG_FILE
fi

# Pull the frontend image from ECR
echo $(date) pulling frontend image $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$DOCKER_FRONTEND_IMAGE:latest | tee -a $LOG_FILE
sudo docker pull $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$DOCKER_FRONTEND_IMAGE:latest

if sudo docker images | grep -q $DOCKER_FRONTEND_IMAGE; then
    echo $(date) "Image pulled out ${DOCKER_FRONTEND_IMAGE}." | tee -a $LOG_FILE
else
    echo $(date) "No image ${DOCKER_FRONTEND_IMAGE}." | tee -a $LOG_FILE
    exit 1
fi
