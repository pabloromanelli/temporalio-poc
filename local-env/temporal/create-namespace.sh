#!/bin/bash

docker run --rm -ti \
  -e TEMPORAL_CLI_ADDRESS=localhost:7233 \
  --entrypoint=bash \
  --network=host \
  temporalio/admin-tools:1.20 -c \
    "tctl --namespace 'default' namespace register --rd '1' --desc 'Default namespace for Temporal Server.'"
