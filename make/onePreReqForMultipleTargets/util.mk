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

allTargets += loc 
loc:                                          ## Count LOC.
	./lib/src.all | wc -l

.PHONY: deps.dot
allTargets += deps.dot
deps.dot:                                     ## Produce a graphviz (.dot) of deps
	$(MAKE) -Bnd -f $(firstword $(MAKEFILE_LIST)) all | make2graph > deps.dot

allTargets += deps.png
deps.png: deps.dot                            ## Produce a png of deps
	dot -Tpng $^ > deps.png

allTargets += deps.view
deps.view: deps.png                           ## View the depenceny graphically
	eog deps.png

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
