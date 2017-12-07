# -*- makefile -*-

include mkutil/bash
include mkutil/begin

define multi
a
b
c
endef

all: useMulti_isFailing useMulti_withExport
	$(begin)

# useMulti_withExport ----------------------------------------------------------

useMulti_withExport: useMulti_withExport_wc
	$(begin)
	[[ "$(shell cat $<)" == "3 3 6" ]]

export multi
useMulti_withExport_wc: useMulti_withExport_file
	$(begin)
	wc < $< | $(end)

export multi
useMulti_withExport_file:
	$(begin)
	echo "$$multi" | $(end)

# useMulti_isFailing -----------------------------------------------------------

useMulti_isFailing: useMulti_rc
	$(begin)
	[[ $(shell cat $<) != 0 ]]

.PHONY: useMulti_rc
useMulti_rc:
	$(begin)
	set +e ; $(MAKE) useMulti ; echo $$? | tee $@

.PHONY: useMulti
useMulti:
	$(begin)
	echo '$(multi)'

# clean ------------------------------------------------------------------------

.PHONY: clean
clean:
	$(begin)
	rm -f useMulti*
