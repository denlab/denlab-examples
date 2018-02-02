include mkutil/bash

#: send node code to evaluate to the running server
query: server-port start
	. ./server-port && curl -d @- http://localhost:$$PORT < chalk.js

#: start the server
start: node_modules
	. ./server-port && bash -xeuo pipefail -c 'npm start & pid=$$! && echo $$pid > $@ ; wait ; rm $@' &
	. ./server-port && shutil/wait-for-it.sh --host=localhost --port=$$PORT

#: install the npm modules
node_modules:
	npm install

#: stop the server
stop:
	. ./server-port && ( ! fuser -n tcp $$PORT || fuser -n tcp $$PORT -k -INT)
