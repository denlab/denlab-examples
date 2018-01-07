start:
	npm start

stop: server-port
	fuser -n tcp -k $$(cat $<)

query: server-port
	curl http://localhost:$(shell cat $<)

.PHONY: server-port
server-port:
	 cat server.js | jq -Rs 'gsub("\\s+";"") | split("listen(") | .[-1] | [splits("[^0-9]")][0]' | jq -r . > $@ 

install:
	npm install

clean:
	rm -f server-port
