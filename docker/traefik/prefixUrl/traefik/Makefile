include mkutil/bash

dc			:= docker-compose
dc_down := $(dc) down --remove-orphans
curl		:= curl -sS --fail


all:	proxy/up test/up proxy/wait_conf_reload query test/down proxy/down
	@:

proxy/log:
	$(dc) logs -f

proxy/up:
	$(dc) up -d

proxy/down:
	$(dc_down)

indir = cd $(dir $@) && 

test/down:
	$(indir) $(dc_down)

test/up:
	$(indir) $(dc) up -d

down: test/down proxy/down
	@:

proxy/wait_conf_reload:
	./shutil/wait-for-expr.sh -c 'f() { set -v && $(curl) --fail http://127.0.0.1/whoami$$1/ &> /dev/null; } && f 1 && f 2'

query:
	$(curl) http://127.0.0.1/whoami1/
	$(curl) http://127.0.0.1/whoami2/

web:
	xdg-open http://localhost:8080

