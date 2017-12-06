include mkutil/bash
include mkutil/chars
include mkutil/begin

.DEFAULT_GOAL := all
all: targets 
	$(begin)

a: 
	@:

cmds != find {conf,server}
# $(info targets:>>>$(nl)$(foreach c,$(cmds),$c:$t$$(begin)$(nl)$(nl)))
# $(eval $(nl)$(foreach c,$(cmds),$c:$t$$(begin)$(nl)$(nl)))

$(info - MAKELEVEL=$(MAKELEVEL))
ifneq (0,$(MAKELEVEL))
  $(info - makelevel != 0)
	$(shell $(MAKE) targets)
else
  $(info - makelevel == 0)
endif
# $(info - .VARIABLES=$(.VARIABLES))
ts != x.ts
targets:
	$(begin)
	find {conf,server} | jq -Rr '"\(.)/$(ts)" | "\(.):\n\t$$(begin)"' | tee $@

include targets

clean:
	$(begin)
	rm -f targets sed.out

