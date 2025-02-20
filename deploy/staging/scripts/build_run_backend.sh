#!/bin/bash
# build_and_run_backend.sh

SCRIPT_DIR=$(cd $(dirname $0) && pwd)
source $SCRIPT_DIR/deploy_config.log

# Check if the container exists (running or stopped)
if docker ps -a --format \'{{.Names}}\' | grep -wq \"$DOCKER_BACKEND_CONTAINER\"; then
    echo \"Stopping and removing container \'$DOCKER_BACKEND_CONTAINER\'...\"
    docker stop \"$DOCKER_BACKEND_CONTAINER\" && docker rm \"$DOCKER_BACKEND_CONTAINER\"
else
    echo \"Container \'$DOCKER_BACKEND_CONTAINER\' does not exist. Skipping removal.\"
fi

# Pull the backend image from ECR
sudo docker pull $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$DOCKER_BACKEND_IMAGE:latest

# Run the backend container
sudo docker run -d \
        --name $DOCKER_BACKEND_CONTAINER \
        --net $DOCKER_NETWORK \
        -p 8080:8080 \
        $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$DOCKER_BACKEND_IMAGE:latest
