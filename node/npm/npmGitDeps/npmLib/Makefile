SHELL=/bin/bash
.SHELLFLAGS=-euo pipefail -c
bin = $(abspath $(NODE_PATH)/../../bin/npmlib)

test: $(bin)
	diff <(npmlib) <(echo  'this is npmlib !')
	touch $@

$(bin): npmlib
	npm install -g

uninstall:
	npm uninstall -g
	! npmlib &> /dev/null

.PHONY: clean
clean:
	rm -r test
