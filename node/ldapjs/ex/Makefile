query: run
	ldapsearch -H ldap://localhost:1389 -x -b o=example 'objectclass=*'

run: node_modules
	npm start &
	wait-for-it.sh -h localhost -p 1389

node_modules:
	npm install

stop:
	fuser -n tcp -k 1389


