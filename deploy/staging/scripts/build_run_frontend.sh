#!/bin/bash
# build_and_run_frontend.sh

set -e  # Stop on error

SCRIPT_DIR=$(cd $(dirname $0) && pwd)
source $SCRIPT_DIR/deploy_config.log
source $AWS_USER_EC2

# Check if the container exists (running or stopped)
if docker ps -a --format \'{{.Names}}\' | grep -wq \"$DOCKER_FRONTEND_CONTAINER\"; then
    echo \"Stopping and removing container \'$DOCKER_FRONTEND_CONTAINER\'...\"
    docker stop \"$DOCKER_FRONTEND_CONTAINER\" && docker rm \"$DOCKER_FRONTEND_CONTAINER\"
else
    echo \"Container \'$DOCKER_FRONTEND_CONTAINER\' does not exist. Skipping removal.\"
fi

# Pull the frontend image from ECR
sudo docker pull $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$DOCKER_FRONTEND_IMAGE:latest

if ! sudo docker images | grep -q $DOCKER_FRONTEND_IMAGE; then
    echo "No image after pull."
    exit 1
fi

# Run the frontend container
sudo docker run -d \
        --name $DOCKER_FRONTEND_CONTAINER \
        --net $DOCKER_NETWORK \
        -p 443:443 \
        $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$DOCKER_FRONTEND_IMAGE:latest

# Verify frontend container running
sudo docker ps | grep $DOCKER_FRONTEND_CONTAINER
