SHELL := bash
.SHELL_FLAGS := -xeuo pipefail -c

domain := $(shell hostname -I | cut -d' ' -f1)

define begin =
@echo $@ | grep --color=always '.*' 1>&2
endef

define begin_ =
$(begin)
@echo $@ >> buildfiles
endef

.PHONY: curl
curl: up
	$(begin)
	curl --cacert cert.pem -X GET https://$(domain)/v2/_catalog
	curl --cacert cert.pem -X GET https://$(domain)/v2/my-alpine/tags/list

push: domainAllowed
	$(begin_)
	docker pull alpine:3.6
	docker tag alpine:3.6 $(domain)/my-alpine
	docker push $(domain)/my-alpine
	docker pull $(domain)/my-alpine

.PHONY: domainAllowed
domainAllowed:
	$(begin_)
ifeq ($(shell ./dockerHostAllowInsecureRegistry $(domain)),false)
	@echo -e '																								\n'\
	'domain $(domain) is not allowed as an insecure registry	\n'\
	'- to add it:																							\n'\
	'  - add {"insecure-registries": ["$(domain)"]}						\n'\
	'  - to /etc/docker/daemon.json														\n'\
	'  - and restart docker																		\n'
	@exit 1
endif

.PHONY: logs
logs:
	$(begin_)
	docker-compose logs -f

.PHONY: down
down:
	$(begin_)
	docker-compose down

.PHONY: up
up:
	$(begin_)
	@$(if $(shell docker-compose ps | tail -n +3 | grep ' Up '),,docker-compose up -d)


#.PHONY: certs.all
#certs.all: certs /etc/docker/certs.d/$(domain)/ca.crt;

#/etc/docker/certs.d/$(domain)/ca.crt: cert.pem
#	sudo cp $^ $@

certs: key.pem cert.pem Makefile
	$(begin_)
	mkdir -p certs
	cp key.pem  certs/domain.key
	cp cert.pem certs/domain.crt

certs/domain.key: key.pem
	$(begin)
	cp $^ $@

certs/domain.crt: cert.pem
	$(begin)
	cp $^ $@

auth/nginx.htpasswd:
	$(begin)
	docker run --rm --entrypoint htpasswd registry:2 -Bbn testuser testpassword > auth/nginx.htpasswd

cert.pem:
	$(begin)
	openssl req -x509 -newkey rsa:4096 -keyout key.pem -out cert.pem -days 365 -nodes

# cert.pem:
# 	$(begin)
# 	echo -e '\n\n\n\n\n\n\n' | openssl req -x509 -newkey rsa:4096 -keyout key.pem -out cert.pem -days 365 -nodes

clean:
	if [[ -f buildfiles ]]; then rm -f $(cat buildfiles) && rm buildfiles ; fi
