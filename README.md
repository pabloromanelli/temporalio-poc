# Temporal.io Worker PoC

- Temporal Cluster + Temporal UI
- Postgres DB
- SpringBoot Worker
- Minimum Helm Chart (based on https://github.com/temporalio/helm-charts)
- Docker Compose to develop locally
- Workflow initialization example (dynamically without using any shared implementation)

## Local Environment

To start the local dev environment:

```bash
cd local-env
# start components (will fail to start temporal server because the DB is not initialized)
./compose-up.sh
# create database schema
./init-db.sh
# start again Temporal server
./compose-up.sh
# create a Temporal "default" namespace
./create-namespace.sh
```

Temporal UI: http://localhost:8081

#### Disclaimers:

- not using the `auto-setup` Docker image to use exactly the same docker image that is going to be deployed in the
  production environment.
- These steps could be automated if we change the entrypoint of the Temporal container to wait until the DB gets created
  and also create another container to wait until the Temporal server got started to create the namespace

## SpringBoot Demo Worker

- Health check endpoint: http://localhost:8080/actuator/health
- Prometheus metrics endpoint: http://localhost:8080/actuator/prometheus
- Graceful worker shutdown

### Docker image
```bash
./gradlew bootBuildImage
```

### Env Variables
- WORKER_clusterHostPort
- workflowQueue
- activitiesQueue
