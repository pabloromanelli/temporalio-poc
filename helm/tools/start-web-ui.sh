#!/bin/bash
docker run --rm -ti \
  --network=host \
  -e TEMPORAL_ADDRESS=host.docker.internal:7233 \
  -p 8080:8080 \
  temporalio/ui:2.10.3
