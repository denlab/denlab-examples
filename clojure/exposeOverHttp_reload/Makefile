port := 18081

query: run
	http --form POST http://localhost:$(port) data='(range 10)'

run:
	bash -xeuo pipefail -c 'unbuffer lein run & pid=$$! && echo $$pid > $@ ; wait ; rm $@' & 
	shutil/wait-for-it.sh --host=localhost --port=$(port)

repl:
	lein repl

#: stop the server
stop:
	! fuser -n tcp $(port) || fuser -n tcp $(port) -k

