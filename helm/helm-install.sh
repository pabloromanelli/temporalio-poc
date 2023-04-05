#!/bin/bash

helm install \
  --namespace temporalio \
  --create-namespace \
  temporal .
