query:
	curl -d '(range 10)' http://localhost:8080

query-stdin:
	curl -d @- http://localhost:8080 <<< '(range 10)'

start:
	unbuffer lein run -m simplehttp.core

stop:
	fuser -n tcp 8080 -k

status:
	fuser -n tcp 8080
