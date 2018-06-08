#!/usr/bin/make -f

# var: common ------------------------------------------------------------------

SHELL       := /bin/bash
.SHELLFLAGS := -euo pipefail -c
# TODO <maybe delete>
null				:=
space				:= $(null) #
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

homeDir := $(dir $_)
mkutil := $(homeDir)mkutil
include $(mkutil)/begin
include $(mkutil)/chars
.DEFAULT_GOAL := all
$(info - homeDir=$(homeDir))
# var: custom -------------------------------------------------------------------


# targets: common ---------------------------------------------------------------

define msg
Error:																																			\n
	- The var `n` (for the [n]ame of the project) must be defined							\n
																																						\n
Example:																																		\n
	n=foo ./template.mk																												\n
	#> Will create a new docker project with the name foo in the current dir.	\n
																																						\n
endef

msg != echo '$(msg) '
files := Makefile Dockerfile docker-compose.yml

.PHONY: all
all: cp $(checkTargets)
	$(begin)
	@if [[ -z '$(n)' ]]; then echo $$'$(msg)' && exit 1; fi
	touch $@

cp: $(foreach f,$(files),$(n)/$(f))
	$(begin)

$(n):
	$(begin)
	if [[ -d $(n) ]]; then echo "dir $(n) already exists!"; fi
	mkdir $(n)

$(eval $(nl)$(foreach f,$(files),$(nl)$(n)/$(f): $(homeDir)$(f) | $(n)$(nl)$(tab)$$(begin)$(nl)$(tab)cp $$< $$@$(nl)$(nl)))

$(n)/docker-compose.yml: $(homeDir)docker-compose.yml | $(n)
	$(begin)
	cat $< | sed -r 's/(\$$\(service\))/$(n)/g' | tee $@

.PHONY: clean
clean:
	$(begin)
	[[ -f buildfiles ]] && rm -f $$(cat buildfiles) buildfiles

