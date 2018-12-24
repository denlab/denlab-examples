
deploy: build fname
	faas-cli deploy -f $$(cat fname).yml

build: fname
	faas-cli build -f $$(cat fname).yml

callme.clean:
	faas-cli rm -f $$(cat fname).yml

faas.ls:
	faas-cli list --verbose

node.run:
	node callme/handler.js

