#!/bin/bash
# build_and_run_frontend.sh

# Pull the frontend image from ECR
docker pull $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/blog-proj-staging-frontend:latest

# Run the frontend container
docker run -d \
        --name blog-proj-staging-frontend-container \
        --net blog2025-stg-docker-network \
        -p 443:443 \
        $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/blog-proj-staging-frontend:latest