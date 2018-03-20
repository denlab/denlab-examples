run:
	docker-compose up --build --exit-code-from controller controller

down:
	docker-compose kill client1 client2 controller nginx protected_server unprotected_server
	docker-compose down --remove-orphans
