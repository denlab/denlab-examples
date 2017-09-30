SHELL=/bin/bash
.SHELLFLAGS=-euo pipefail -c
bin = $(abspath $(NODE_PATH)/../../bin/npmapp)

test: $(bin)
	diff <(npmapp) <(echo  'this is npmapp !')
	touch $@

$(bin): npmapp
	npm install -g

uninstall:
	npm uninstall -g
	! npmapp &> /dev/null

.PHONY: clean
clean:
	rm -r test
