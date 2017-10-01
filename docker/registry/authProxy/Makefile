SHELL := bash
.SHELL_FLAGS := -xeuo pipefail -c

domain := $(shell hostname -I | cut -d' ' -f1)

define begin =
@echo $@ | GREP_COLOR='0;44' grep --color=always '.*' 1>&2
endef

define begin_ =
$(begin)
@echo $@ >> buildfiles
endef

.PHONY: all
all: curl push
	$(begin)

.PHONY: push
push: domainAllowed
	$(begin_)
	docker login -u=testuser -p=testpassword $(domain)
	docker pull alpine:3.6
	docker tag alpine:3.6 $(domain)/my-alpine
	docker push $(domain)/my-alpine
	docker pull $(domain)/my-alpine

.PHONY: curl
curl: up domainAllowed
	$(begin)
	curl --cacert cert.pem --user testuser:testpassword -X GET https://$(domain)/v2/_catalog
	curl --cacert cert.pem --user testuser:testpassword -X GET https://$(domain)/v2/my-alpine/tags/list

log:
	$(begin)
	docker login -u=testuser -p=testpassword -e=root@example.ch $(domain):5043

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
	docker-compose up -d

# .PHONY: cert.docker
# cert.docker: cert.pem 
# 	sudo cp cert.pem /etc/docker/certs.d/$(domain)/ca.cert

/etc/docker/certs.d/$(domain)/ca.cert: cert.pem
	$(begin)
	sudo cp $^ $@

.PHONY: certs
certs: auth/domain.key auth/domain.crt /etc/docker/certs.d/$(domain)/ca.cert
	$(begin)

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

auth/domain.key: key.pem;
	$(begin)
	cp $^ $@

auth/domain.crt: cert.pem
	$(begin)
	cp $^ $@

auth/nginx.htpasswd:
	$(begin)
	docker run --rm --entrypoint htpasswd registry:2 -Bbn testuser testpassword > auth/nginx.htpasswd

cert.pem:
	$(begin)
	openssl req -x509 -newkey rsa:4096 -keyout key.pem -out cert.pem -days 365 -nodes
