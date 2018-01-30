include mkutil/bash

cnt := my-openldap-container

all: stop query
	@:

query: run
	docker exec $(cnt) ldapsearch -x -H ldap://localhost -b dc=example,dc=org -D "cn=admin,dc=example,dc=org" -w admin

run:
	mkdir -p build
	docker run -d --name $(cnt) -p 636 -p 389 osixia/openldap:1.1.11
	docker logs -f $(cnt) &> build/log &
	shutil/wait-for-expr.sh -c 'grep "slapd starting" build/log'

stop:
	docker rm -f $(cnt)

clean:
	rm -rf build
