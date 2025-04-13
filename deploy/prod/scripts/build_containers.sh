#!/bin/bash
# build_and_run_backend.sh

set -e  # Stop on error

SCRIPT_DIR=$(cd $(dirname $0) && pwd)
source $SCRIPT_DIR/deploy_config.log
source $AWS_USER_EC2

# Must remove the old shared volume first.
# [project name = directory containing docker-compose.yml]_[volume name]
SHARED_VOLUME=scripts_frontend-access

if sudo docker volume ls --format {{.Name}} | grep -wq $SHARED_VOLUME; then
    echo $(date) docker volume ${SHARED_VOLUME} exists. delete it. | tee -a $LOG_FILE
    sudo docker volume rm ${SHARED_VOLUME}
else
    echo $(date) docker volume ${SHARED_VOLUME} is absent. | tee -a $LOG_FILE
fi

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
