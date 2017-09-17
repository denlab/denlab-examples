# <help> -----------------------------------------------------------------------
.DEFAULT_GOAL := help
.PHONY: help

space := # 
# <template1> --------------------------------------------------------------------------------
define rul =
allTargets += $(1)
$(1): $(2)
	$(3)
endef

$(eval $(call rul,rul.test,sys.info,echo hi this is the test rul))

$(eval $(call rul,rul.test.tmpl,,@echo this is a templatized rule))
# </template1> -------------------------------------------------------------------------------

# <template2> --------------------------------------------------------------------------------
define rul2 =
:	
endef
# <template2> -------------------------------------------------------------------------------
define nl =


endef

allTargets += help
help: ## show this help 
	@echo $(MAKEFILE_LIST) | xargs grep -hE '^[^ ]+:.*?## .*$$' | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'
# <help> -----------------------------------------------------------------------

.PHONY: sys.info
allTargets += sys.info
sys.info: ## show system info: makevariables: .SHELL, SHELL, .SHELLFLAGS, etc.
	ps af | grep --color=always -C6 $$$$
	$(info $(shell jq -cCn '{".SHELL": "$(.SHELL)", "SHELL": "$(SHELL)", ".SHELLFLAGS": "$(.SHELLFLAGS)", foo: "bar", jqQuoteFn: $(jqQuoteFn)}'))
	$(info allTargets=$(allTargets))

define show.make.variables = 
	VARS_OLD := $(.VARIABLES)
	CUR-DIR := $(shell pwd)
	LOG-DIR := $(CUR-DIR)/make-logs
$(foreach v,                                        \
  $(filter-out $(VARS_OLD) VARS_OLD,$(.VARIABLES)), \
  $(info $(v) = $($(v))))
endef

sys.vars2: sys.vars

.PHONY: sys.cmd
sys.cmd: ## show make command line invocation
	$(info MAKE=$(MAKE) MAKEFLAGS=$(MAKEFLAGS) MAKECMDGOALS=$(MAKECMDGOALS))

.PHONY: sys.vars
sys.vars: ## show make variables
	$(show.make.variables)

allTargets += loc 
loc:                                          ## Count LOC.
	./lib/src.all | wc -l

.PHONY: deps.dot
allTargets += deps.dot
deps.dot:                                     ## Produce a graphviz (.dot) of deps
	$(MAKE) --dry-run --debug=a -f $(firstword $(MAKEFILE_LIST)) $(t) | make2graph > deps.dot

# $(ifndef t \
# 	$(error $(nl)\
# $(nl)\
# ERROR$(nl)\
#   - variable t must be defined $(nl)\
#   - example: make $(MAKECMDGOALS) t=all$(nl)\
# $(nl)\
# endif
# )

allTargets += deps.png
deps.png: deps.dot                            ## Produce a png of deps
	dot -Tpng $^ > deps.png

allTargets += deps.view
deps.view: deps.png                           ## View the depenceny graphically
	eog deps.png

.PHONY: deps.view.text.ascii
deps.view.text.ascii: deps.dot ## view deps in ascii
	graph-easy --from graphviz -as ascii < deps.dot	

deps.view.text.boxart: deps.dot ## view deps in boxart
	graph-easy --from graphviz -as boxart < deps.dot	

.PHONY: d
d:
	echo $(firstword $(MAKEFILE_LIST))
	echo $(allTargets)

listRaw: ## Call emacs test
	@$(MAKE) -pRrq -f $(firstword $(MAKEFILE_LIST)) : 2>/dev/null 

# echo $(firstword $(MAKEFILE_LIST))
# echo $(MAKEFILE_LIST)
listTargets.util: $(last $(MAKEFILE_LIST)) ## List all targets $(MAKEFILE_LIST)
	@$(MAKE) -pRrq -f $(firstword $(MAKEFILE_LIST)) : 2>/dev/null | awk -v RS= -F: '/^# File/,/^# Finished Make data base/ {if ($$1 !~ "^[#.]") {print $$1}}' | sort | egrep -v -e '^[^[:alnum:]]' -e '^$@$$' | xargs > $@

listTargets: $(firstword $(MAKEFILE_LIST)) ## List all targets $(MAKEFILE_LIST)
	@$(MAKE) -pRrq $(foreach mk,$(MAKEFILE_LIST),-t $(mk)) -f $(firstword $(MAKEFILE_LIST)) : 2>/dev/null | awk -v RS= -F: '/^# File/,/^# Finished Make data base/ {if ($$1 !~ "^[#.]") {print $$1}}' | sort | egrep -v -e '^[^[:alnum:]]' -e '^$@$$' | xargs > $@

listTargets.mainOnly: $(MAKEFILE_LIST) ## List only the targets of the main makefile 
	@$(MAKE) -pRrq -f $(firstword $(MAKEFILE_LIST)) : 2>/dev/null | awk -v RS= -F: '/^# File/,/^# Finished Make data base/ {if ($$1 !~ "^[#.]") {print $$1}}' | sort | egrep -v -e '^[^[:alnum:]]' -e '^$@$$' | xargs > $@
