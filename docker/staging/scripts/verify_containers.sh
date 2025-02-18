#!/bin/bash
# verify_containers.sh

# Verify backend container is running
docker ps | grep backend-container

# Verify frontend container is running
docker ps | grep frontend-container
