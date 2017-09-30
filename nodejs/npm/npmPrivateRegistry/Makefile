consume-module: publish-module
	cd npmModuleUser && npm install --save npmmodule

consume-module.from-npmjs.org: publish-module
	cd npmModuleUser && npm install --save chalk


publish-module: adduser registerLocalMachine
	cd npmModule && npm publish

registerLocalMachine:
	echo -e 'u\nu\nu@u.com\n' | npm adduser

adduser: npm.set.registry.private dockerRun
	echo -e 'u\nu\nu@u.com\n' | docker exec -it sinopia npm adduser --registry http://localhost:4873/

npm.set.registry.standard:
	npm set registry https://registry.npmjs.org/

npm.set.registry.private:
	npm set registry http://$(hostname -I  | cut -d' ' -f1):4873/

dockerRun:
	if ! docker ps | grep -q sinopia; then docker run --name sinopia -d -p 4873:4873 keyvanfatehi/sinopia:latest ; fi 

clean:
	docker stop sinopia
	npm set registry https://registry.npmjs.org/
