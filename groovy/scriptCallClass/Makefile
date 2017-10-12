#!/usr/bin/make -f

SHELL       := /bin/bash
.SHELLFLAGS := -euo pipefail -c

define begin =
	@echo $@ >> buildfiles
	@echo $@ | GREP_COLOR="01;44" egrep --color=always ".*" 1>&2
endef

run:
	$(begin)
	groovy com/printThingScript.groovy

.PHONY: clean
clean:
	$(begin)
	[[ -f buildfiles ]] && rm -f $$(cat buildfiles) buildfiles



