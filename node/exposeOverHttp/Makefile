include mkutil/bash

#: send node code to evaluate to the running server
query: server-port start
	curl -d @- http://localhost:8080 < chalk.js

#: start the server
start: node_modules
	bash -xeuo pipefail -c 'npm start & pid=$$! && echo $$pid > $@ ; wait ; rm $@' &
	shutil/wait-for-it.sh --host=localhost --port=8080

#: install the npm modules
node_modules:
	npm install

#: stop the server
stop:
	! fuser -n tcp 8080 || fuser -n tcp 8080 -k -INT
