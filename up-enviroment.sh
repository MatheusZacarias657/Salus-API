#!/bin/bash
set -e
echo "Build Applications.."

if [ -z "$1" ]; then
    docker build -t salus-api -f Sources/Dockerfile-API .
elif [ "$1" = "no-build" ]; then
    echo "No Build Requested"
fi

echo ""
echo "Trying to up Infrastructure..."
docker-compose --project-name salus -f docker-compose-infra.yml up -d --no-recreate

echo ""
echo "Trying to up the System..."
docker-compose --project-name salus -f docker-compose-source.yml up -d

