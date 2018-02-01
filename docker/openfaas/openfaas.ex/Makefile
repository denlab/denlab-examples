include mkutil/bash

$(HOME)/repos/github.com/openfaas/faas:
	  set -xv																											\
	&& mkdir -p $(dir $@)																					\
	&& cd $(dir $@)																								\
	&& (test -d $@ || git clone https://github.com/openfaas/faas) \
	&& cd $@																											\
	&& git pull --ff-only																					\
	&& touch $@

swarm_init:
	ip=$$(hostname -I | jq -R '[splits(" ")][0]' | jq -r '.') \
	&& docker swarm init --advertise-addr $$ip
