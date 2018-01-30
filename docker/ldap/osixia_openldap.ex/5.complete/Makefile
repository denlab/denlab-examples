include mkutil/bash

ser := ldap

all: query
	@:

query: $(if $(nd),,run)
	./query

query-internal: $(if $(nd),,run)
	docker-compose exec $(ser) \
		ldapsearch -x -H ldap://localhost -b dc=example,dc=org -D "cn=admin,dc=example,dc=org" -w JonSn0w

run:
	mkdir -p build
	docker-compose up --build -d
	docker-compose logs -f $(ser) |& tee build/log 1>&2 &
	shutil/wait-for-expr.sh -c 'grep "slapd starting" build/log'

stop:
	docker-compose down --remove-orphans

clean:
	rm -rf build

help:
	docker-compose run $(ser) --help

help.ldapsearch:
	docker-compose run $(ser) ldapsearch --help

exec_sh:
	docker-compose exec $(ser) sh
# ldapsearch -x -h 10.0.2.15 -p 32957 -b dc=example,dc=org -D "cn=admin,dc=example,dc=org" -w JonSn0w
