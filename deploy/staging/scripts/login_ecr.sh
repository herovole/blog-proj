#!/bin/bash
set -e  # Stop on error


SCRIPT_DIR=$(cd $(dirname $0) && pwd)
source $SCRIPT_DIR/deploy_config.log

# Download the file from S3

aws s3 ls s3://blog2025-stg-s3-private/
aws s3 ls s3://blog2025-stg-s3-private/build/

aws s3 cp ${AWS_USER_S3} ${AWS_USER_EC2}

# Read the file
source $AWS_USER_EC2

# Authenticate to ECR
aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com
