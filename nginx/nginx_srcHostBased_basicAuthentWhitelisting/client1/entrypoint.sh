#!/usr/bin/env bash
set -xveuo pipefail
wait-for-it.sh -s nginx:80         -- echo -e "\nnginx started!!\n"
wait-for-it.sh -s http_server:8080 -- echo -e '\nhttp_server started!\n'
curl --fail --user user:pwd http://nginx | grep -q 'hello world'
sleep 9999999
