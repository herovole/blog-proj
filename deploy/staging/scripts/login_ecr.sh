#!/bin/bash
set -e  # Stop on error

# Define S3 bucket and file
S3_URI="s3://blog2025-stg-s3-private/build/aws_user.log"
DEST_PATH="/home/ec2-user/app/aws_user.log"

# Download the file from S3
aws s3 cp S3_URI $DEST_PATH

# Read the file
source $DEST_PATH

# Authenticate to ECR
aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com
