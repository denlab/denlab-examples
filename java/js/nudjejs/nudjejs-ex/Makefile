
SHELL       := /bin/bash
.SHELLFLAGS := -euo pipefail -c

d := build
$(shell mkdir -p $d)

.PHONY: browse
browse: $d/url
	xdg-open $(shell cat $<)	

$d/url: run
	echo 'http://localhost:5050' > $@

.PHONY: run
run: $d/C.class
	fuser -n tcp 5050 -k || :
	cd $(dir $<) && java C &
	wait-for-it.sh -h localhost -p 5050

$d/%.class: %.java
	cp $< $d && cd $d && javac $<

.PHONY: clean
clean:
	fuser -n tcp 5050 -k || :
	rm -rf build
