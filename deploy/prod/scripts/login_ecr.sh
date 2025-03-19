#!/bin/bash
set -e  # Stop on error

SCRIPT_DIR=$(cd $(dirname $0) && pwd)
source $SCRIPT_DIR/deploy_config.log

echo $(date) Downloading S3 aws_user file. | tee -a $LOG_FILE
# Download the file from S3
aws s3 cp ${AWS_USER_S3} ${AWS_USER_EC2}

# Read the file
source $AWS_USER_EC2

echo $(date) Login to ECR. $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com | tee -a $LOG_FILE
# Authenticate to ECR
aws ecr get-login-password --region $AWS_REGION | sudo docker login --username AWS --password-stdin $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com
