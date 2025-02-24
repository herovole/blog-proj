#!/bin/bash
# build_and_run_frontend.sh

set -e  # Stop on error

SCRIPT_DIR=$(cd $(dirname $0) && pwd)
source $SCRIPT_DIR/deploy_config.log
source $AWS_USER_EC2

echo $(date) deploying frontend container $DOCKER_FRONTEND_CONTAINER | tee -a $LOG_FILE

# Check if the container exists (running or stopped)
if sudo docker ps -a --format \'{{.Names}}\' | grep -wq \"$DOCKER_FRONTEND_CONTAINER\"; then
    echo $(date) \"Stopping and removing container \'$DOCKER_FRONTEND_CONTAINER\'...\" | tee -a $LOG_FILE
    sudo docker stop \"$DOCKER_FRONTEND_CONTAINER\"
    sudo docker rm \"$DOCKER_FRONTEND_CONTAINER\"
else
    echo $(date) \"Container \'$DOCKER_FRONTEND_CONTAINER\' does not exist. Skipping removal.\" | tee -a $LOG_FILE
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

# Run the frontend container
sudo docker run -d \
        --name $DOCKER_FRONTEND_CONTAINER \
        --net $DOCKER_NETWORK \
        -p 443:443 \
        $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$DOCKER_FRONTEND_IMAGE:latest

# Verify frontend container running
if sudo docker ps | grep $DOCKER_FRONTEND_CONTAINER; then
    echo $(date) "${DOCKER_FRONTEND_CONTAINER} deployed" | tee -a $LOG_FILE
else
    echo $(date) "${DOCKER_FRONTEND_CONTAINER} failed to get deployed" | tee -a $LOG_FILE
    exit 1
fi
