#!/usr/bin/make -f

# var: common ------------------------------------------------------------------

SHELL       := /bin/bash
.SHELLFLAGS := -euo pipefail -c
# TODO <maybe delete>
null				:=
space				:= $(null) #
define nl		:=

endef

colorize			= { GREP_COLOR="01;$1" egrep --color=always ".*"  || true ; }

define begin	=
	@echo $@ >> buildfiles
	@echo -n '$@: ' | $(call colorize,45) 1>&2
	@echo '$^' | tr ' ' '\n' | sed -r 's/.*/  - &/' | $(call colorize,35) 1>&2
endef

define end		=
		tee $@ | $(call colorize,30) 1>&2
endef

define stdout =
		tee $@ | $(call colorize,30) 1>&2
endef

# var: custom -------------------------------------------------------------------

# targets: common ---------------------------------------------------------------
include mkutil/vars

$(call vars)

all:
	$(begin)
	touch $@
a:
	$(begin)

b:
	$(begin)

c:
	$(begin)

.PHONY: clean
clean:
	$(begin)
	[[ -f buildfiles ]] && rm -f $$(cat buildfiles) buildfiles

# targets: custom ---------------------------------------------------------------


