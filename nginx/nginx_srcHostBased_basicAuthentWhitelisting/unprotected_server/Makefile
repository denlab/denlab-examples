img := node-http-server
cnt := node-http-server

run: build
	docker run --rm --name $(cnt) $(img)

build:
	docker build . -t $(img)
