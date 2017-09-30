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

npmlib.add:
	 npm install 'git+ssh://git@github.com:denlab/denlab-examples.git#repo/npm/npmlib'

npmlib.rm:
	 npm uninstall 'git+ssh://git@github.com:denlab/denlab-examples.git#repo/npm/npmlib'

.PHONY: clean
clean:
	rm -r test
