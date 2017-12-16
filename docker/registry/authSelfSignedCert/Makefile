include mkutil/bash
include mkutil/begin

domain := $(shell hostname -I | cut -d' ' -f1)
docker_host_cert := /etc/docker/certs.d/$(domain)/ca.crt

# targets ----------------------------------------------------------------------

.PHONY: curl
curl: up
	$(begin)
	curl --cacert cert.pem -X GET https://$(domain)/v2/_catalog
	curl --cacert cert.pem -X GET https://$(domain)/v2/my-alpine/tags/list

push:  domainAllowed
	$(begin)
	docker pull alpine:3.6
	docker tag  alpine:3.6 $(shell cat $<)/my-alpine
	docker push $(shell cat $<)/my-alpine
	docker pull $(shell cat $<)/my-alpine

# <docker-insecure registry> ---------------------------------------------------

docker_info:
	$(begin)
	docker info | $(end)

.PHONY: domainAllowed
domainAllowed: docker_is_allow_insecure_registries
	$(begin)
	if ! $(shell cat $<); then                                                     \
		echo -e '																																	\n'\
	'domain $(domain) is not allowed as an insecure registry	\n'\
	'- to add it:																																\n'\
	'  - add {"insecure-registries": ["$(domain)"]}						\n'\
	'  - to /etc/docker/daemon.json																							\n'\
	'  - and restart docker																											\n'\
		&& exit 1;                                                                   \
	fi

docker_is_allow_insecure_registries: docker_info
	$(begin)
		cat $<                                     \
	| jq -Rs                                     \
	'  split("Insecure Registries:\n")[-1]			'\
	'| [splits("\n[^ ]")][0]										'\
	'| ltrimstr(" ")														'\
	'| split("\n ")															'\
	'| any(. == "$(domain)")	'\
	| $(end)

# </docker-insecure registry> --------------------------------------------------

# <docker-compose-targets> -----------------------------------------------------

.PHONY: logs
logs:
	$(begin)
	docker-compose logs -f

.PHONY: down
down:
	$(begin)
	docker-compose down

.PHONY: up
up:
	$(begin)
	@$(if $(shell docker-compose ps | tail -n +3 | grep ' Up '),,docker-compose up -d)

# <docker-compose-targets> -----------------------------------------------------

# <certs-targets> --------------------------------------------------------------

.PHONY: _certs_all
certs.all: $(docker_host_cert) certs
	$(begin)

$(docker_host_cert): cert.pem
	$(begin)
	sudo cp -b $^ $@

.PHONY: _certs
_certs: certs/domain.key certs/domain.crt
	$(begin)

certs:
	$(begin)
	mkdir -p certs

certs/domain.key: key.pem | certs
	$(begin)
	cp $^ $@

certs/domain.crt: cert.pem | certs
	$(begin)
	cp $^ $@

# </certs-targets> --------------------------------------------------------------

auth/nginx.htpasswd:
	$(begin)
	docker run --rm --entrypoint htpasswd registry:2 -Bbn testuser testpassword > auth/nginx.htpasswd

cert.pem:
	$(begin)
	openssl req -x509 -newkey rsa:4096 -keyout key.pem -out cert.pem -days 365 -nodes

# cert.pem:
#		$(begin)
#		echo -e '\n\n\n\n\n\n\n' | openssl req -x509 -newkey rsa:4096 -keyout key.pem -out cert.pem -days 365 -nodes

include mkutil/clean

clean_rm: files=docker_info docker_is_allow_insecure_registries domain callgrind.* cert.* certs clean_rm_files data key.pem *.pem
clean_rm:
	$(begin)
	rm -rf data certs clean_rm_files
