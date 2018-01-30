include mkutil/bash

ser := ldap

all: query
	@:

query: $(if $(nd),,run)
	docker-compose exec $(ser) ldapsearch -x -H ldap://localhost -b dc=example,dc=org -D "cn=admin,dc=example,dc=org" -w admin

run:
	mkdir -p build
	docker-compose up --build -d
	docker-compose logs -f $(ser) |& tee build/log 1>&2 &
	shutil/wait-for-expr.sh -c 'grep "slapd starting" build/log'

stop:
	docker-compose down --remove-orphans

clean:
	rm -rf build

