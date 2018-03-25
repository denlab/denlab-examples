.PHONY: up
up: 								#: start all services
	docker-compose up --build --exit-code-from gdrive
	docker-compose logs -f

.PHONY: clean
clean:											#: remove build files
	docker-compose --remove-orphans
