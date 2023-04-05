#!/bin/bash

helm upgrade \
  --namespace temporalio \
  temporal .
