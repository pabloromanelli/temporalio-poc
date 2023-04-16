#!/bin/bash

# creating namespace 'default'
# 7 day retention
docker run --rm -ti \
  -e TEMPORAL_CLI_ADDRESS=localhost:7233 \
  --entrypoint=bash \
  --network=host \
  temporalio/admin-tools:1.20 -c \
    "tctl --namespace 'default' namespace register --retention '7' --desc 'Default namespace for Temporal Server.'"
