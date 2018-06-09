.PHONY: up
up: service									#: start all services
	docker-compose up -d --build
	docker-compose logs -f

.PHONY: down
down: service								#: stop all services
	docker-compose down "$@"
	docker-compose logs -f

.PHONY: run
run_shell: service					#: run a interactive shell
	docker-compose build
	docker-compose run $(shell cat $<) sh

.PHONY: run
run: service								#: run the unique service of this docker-compose
	docker-compose build
	docker-compose run $(shell cat $<)

service: docker-compose.yml	#: create the file containing the name of the service
	docker-compose config | sed -rn '/services:/,$$ p' | head -n 2 | tail -1 | tr -d ' :' | tee $@

.PHONY: clean
clean:											#: remove build files
	rm -f service 
