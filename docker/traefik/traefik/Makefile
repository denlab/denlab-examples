include mkutil/bash

diff != type diff.color &> /dev/null && echo 'git_diff_opts="--color=always" diff.color' || diff
dc := docker-compose
dc_down := $(dc) down --remove-orphans

all: prox/up test/up test/ps test/scale test/ps prox/query prox/query/diff down
	@:

prox/up:
	$(dc) up -d

prox/down:
	$(dc_down)

indir = cd $(dir $@) && 

test/down:
	$(indir) $(dc_down)

test/up:
	$(indir) $(dc) up -d

test/scale:
	$(indir) $(dc) scale whoami=2

test/logs:
	$(indir) $(dc) logs -f

test/ps:
	$(indir) $(dc) ps

prox/query:
	curl -H Host:whoami.docker.localhost http://127.0.0.1

makeQuiet = $(MAKE) --no-print-directory
makeQuery = $(makeQuiet) prox/query

prox/query/diff:
	$(diff) <($(makeQuery)) <($(makeQuery)) || :

ps: prox/ps test/ps
	@:

down: test/down test/down
	@:
