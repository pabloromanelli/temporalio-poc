#!/bin/bash

docker run --rm -ti \
  -e SQL_HOST=localhost \
  -e SQL_USER=temporal \
  -e SQL_PASSWORD=temporal \
  -e SQL_PORT=5432 \
  -e SQL_PLUGIN=postgres12 \
  --entrypoint=bash \
  --network=host \
  temporalio/admin-tools:1.20 -c "
      temporal-sql-tool --db temporal create-database && \
      temporal-sql-tool --db temporal setup-schema -v 0.0 && \
      temporal-sql-tool --db temporal update-schema -d ./schema/postgresql/v12/temporal/versioned && \
      temporal-sql-tool --db temporal_visibility create-database && \
      temporal-sql-tool --db temporal_visibility setup-schema -v 0.0 && \
      temporal-sql-tool --db temporal_visibility update-schema -d ./schema/postgresql/v12/visibility/versioned"
