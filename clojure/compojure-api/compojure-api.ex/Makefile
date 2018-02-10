run:
	lein ring server

test:
	lein test

run_standalone:
	lein do clean, ring uberjar
	java -jar target/server.jar

uberwar:
	lein ring uberwar
