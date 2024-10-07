#!/bin/bash
CURRENTDIR=$(realpath $(dirname $0))
BASEDIR=$(realpath $(dirname $(dirname $0)))

set -e
echo "Build Applications.."

if [ -z "$1" ]; then
    docker build -t salus-api -f $BASEDIR/Sources/Dockerfile-API .
    docker build -t rabbitmq-custom -f $BASEDIR/Dockerfile-RabbitMQ .
elif [ "$1" = "no-build" ]; then
    echo "No Build Requested"
fi

echo ""
echo "Trying to up Infrastructure..."

echo ""
echo "Up databases..."
docker-compose --project-name salus -f $BASEDIR/docker-compose-database.yml up -d --no-recreate

echo ""
echo "Up external services..."
docker-compose --project-name salus -f $BASEDIR/docker-compose-service.yml up -d --no-recreate

echo ""
echo "Trying to up the System..."
docker-compose --project-name salus -f $BASEDIR/docker-compose-source.yml up -d

echo "All Containers was up!"
