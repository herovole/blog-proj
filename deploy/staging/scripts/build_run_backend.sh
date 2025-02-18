#!/bin/bash
# build_and_run_backend.sh

# Pull the backend image from ECR
docker pull $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/blog-proj-staging-backend:latest

# Run the backend container
docker run -d \
        --name blog-proj-staging-backend-container \
        --net blog2025-stg-docker-network \
        -p 8080:8080 \
        $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/blog-proj-staging-backend:latest
