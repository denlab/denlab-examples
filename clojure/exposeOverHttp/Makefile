#: query the server to evaluate code
query: start
	curl -d '(range 10)' http://localhost:8080

#: start the server
start:
	bash -xeuo pipefail -c 'unbuffer lein run -m simplehttp.core & pid=$$! && echo $$pid > $@ ; wait ; rm $@' &
	shutil/wait-for-it.sh --host=localhost --port=8080

#: stop the server
stop:
	! fuser -n tcp 8080 || fuser -n tcp 8080 -k

