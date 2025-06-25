#!/bin/bash

./mvnw spring-boot:build-image
docker-compose up --build -d

port=8080
host=http://localhost

is_running() {
    service="$1"
    container_id="$(docker-compose ps -q "$service")"
    container_status="$(docker inspect -f "{{.State.Running}}" "$container_id")"
    if [ "$container_status" == true ]; then
        return 0
    else
        return 1
    fi
}
while ! is_running java-hello; do sleep 1; done

is_greeting() {
    retries=$(($retries + 1))
    url="${host}:${port}"
    response=$(curl "$url")
    if [ "$response" == "Greetings from Spring Boot!" ]; then
        echo "Status 200, all services running: $response"
        return 0
    elif [ "$retries" == 5 ]; then
        echo "Service failed, after $retries time(s)"
        return 0
    else 
        echo "Checking if services is up : $retries time(s)"
        return 1
    fi
}

retries=0

while ! is_greeting "$retries" "$host" "$port"; do sleep 5; done
