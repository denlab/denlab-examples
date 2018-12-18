SHELL       := /bin/bash
.SHELLFLAGS := -euo pipefail -c

define usage

usage: make cmd cmd=<cmd>
example:
  - cmd: make cmd cmd=help

endef
export usage

work/ex.tf: ex.tf Makefile
	sed -r 's/\$$\{DOCKER_SERVER\}/'"$${hostIp}/g" $< > $@.tmp && mv $@.tmp $@

run-sh: work/ex.tf
	mkdir -p work
	docker run																								\
		--entrypoint sh																					\
		-it																											\
		-w           /work																			\
		-v           $(PWD)/work:/work													\
		-v           /var/run/docker.sock:/var/run/docker.sock	\
		-e DOCKER_SERVER=$(hostIp)                              \
		hashicorp/terraform:light

run:
	@if [[ '$(cmd)' == '' ]]; then echo "$${usage}"; exit 1; fi
	mkdir -p work
	docker run -it -w /work -v $(pwd)/work:/work hashicorp/terraform:light $(cmd)
