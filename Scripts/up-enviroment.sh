#!/bin/bash
CURRENTDIR=$(realpath $(dirname $0))
BASEDIR=$(realpath $(dirname $(dirname $0)))

WAIT_FOR_IT="$CURRENTDIR/wait-for-it.sh"

set -e
echo "Build Applications.."

if [ -z "$1" ]; then
    docker build -t salus-api -f $BASEDIR/Sources/Dockerfile-api .
    docker build -t salus-communicator -f $BASEDIR/Sources/Dockerfile-communicator .
    docker build -t salus-notificator -f $BASEDIR/Sources/Dockerfile-notificator .
    docker build -t rabbitmq-custom -f $BASEDIR/Dockerfile-RabbitMQ .
    docker build -t salus-whatsapp -f $BASEDIR/Dockerfile-whatsapp .
elif [ "$1" = "no-build" ]; then
    echo "No Build Requested"
else
     echo "Specific Build Requested"
     docker build -t salus-$1 -f $BASEDIR/Sources/Dockerfile-$1 .
fi

echo ""
echo "Trying to up Infrastructure..."

echo ""
echo "Up databases..."
docker-compose --project-name salus -f $BASEDIR/docker-compose-database.yml up -d --no-recreate

echo ""
echo "Up external services..."
docker-compose --project-name salus -f $BASEDIR/docker-compose-service.yml up -d --no-recreate

$WAIT_FOR_IT localhost:1433 --timeout=60 --strict -- echo "Service is up"
$WAIT_FOR_IT localhost:27017 --timeout=60 --strict -- echo "Service is up"
$WAIT_FOR_IT localhost:6379 --timeout=60 --strict -- echo "Service is up"
$WAIT_FOR_IT localhost:15672 --timeout=60 --strict -- echo "Service is up"
$WAIT_FOR_IT localhost:5672 --timeout=60 --strict -- echo "Service is up"

echo ""
echo "Trying to up the System..."
docker-compose --project-name salus -f $BASEDIR/docker-compose-source.yml up -d

echo "All Containers was up!"
