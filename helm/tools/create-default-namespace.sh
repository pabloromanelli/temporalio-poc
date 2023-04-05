#!/bin/bash
# creating namespace 'default' with 7 day retention
docker run --rm -ti \
  --network=host \
  -e TEMPORAL_CLI_ADDRESS=host.docker.internal:7233 \
  --entrypoint=bash \
  temporalio/admin-tools:1.20 -c \
    "tctl --namespace 'default' namespace register --retention '7' --desc 'Default namespace'"
