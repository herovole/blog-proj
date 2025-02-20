#!/bin/bash
# build_and_run_frontend.sh

SCRIPT_DIR=$(cd $(dirname $0) && pwd)
source $SCRIPT_DIR/deploy_config.log

# Check if the container exists (running or stopped)
if docker ps -a --format \'{{.Names}}\' | grep -wq \"$DOCKER_FRONTEND_CONTAINER\"; then
    echo \"Stopping and removing container \'$DOCKER_FRONTEND_CONTAINER\'...\"
    docker stop \"$DOCKER_FRONTEND_CONTAINER\" && docker rm \"$DOCKER_FRONTEND_CONTAINER\"
else
    echo \"Container \'$DOCKER_FRONTEND_CONTAINER\' does not exist. Skipping removal.\"
fi

# Pull the frontend image from ECR
sudo docker pull $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$DOCKER_FRONTEND_IMAGE:latest

# Run the frontend container
sudo docker run -d \
        --name $DOCKER_FRONTEND_CONTAINER \
        --net $DOCKER_NETWORK \
        -p 443:443 \
        $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$DOCKER_FRONTEND_IMAGE:latest