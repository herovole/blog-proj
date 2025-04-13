#!/bin/bash
# build_and_run_backend.sh

set -e  # Stop on error

SCRIPT_DIR=$(cd $(dirname $0) && pwd)
source $SCRIPT_DIR/deploy_config.log
source $AWS_USER_EC2

echo $(date) run containers $DOCKER_BACKEND_CONTAINER and $DOCKER_FRONTEND_CONTAINER | tee -a $LOG_FILE

sudo docker compose \
  -f "$SCRIPT_DIR/docker-compose.yml" \
  --env-file "$SCRIPT_DIR/deploy_config.log" \
  --env-file "$AWS_USER_EC2" \
  build | tee -a "$LOG_FILE"

sudo docker compose \
  -f "$SCRIPT_DIR/docker-compose.yml" \
  --env-file "$SCRIPT_DIR/deploy_config.log" \
  --env-file "$AWS_USER_EC2" \
  up -d | tee -a "$LOG_FILE"
