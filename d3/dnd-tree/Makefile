http-server.start: node_modules flare.json
	http-server -o -c-1

flare.json: flare-example.json
	ln -s $< $@

node_modules:
	npm install
