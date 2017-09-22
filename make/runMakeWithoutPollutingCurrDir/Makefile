#!/usr/bin/make -f
buildScript := jsonToXml.test
wdir := $(HOME)/.utils/$(buildScript)/build
p    := $(wdir)
VPATH := $(wdir)

SHELL=/bin/bash
.SHELLFLAGS=-euo pipefail -c
jqQuoteFn := ([39]|implode)
comma := ,
verbose :=

showUtilHelp := false
include util.mk
jobName := jobdsl/seed


debug := 

define init =
mkdir -p $p
ln -sf $p
endef

define begin=
$(init)
echo $@:  | grep -C99999 --color=always '.*' 1>&2
$(if $(debug), echo - target="$@" && echo - prereq=$^)
$(foreach pr,$^,cat $(p)/$(pr))
endef
#bash -c "if [[ -d $(wdir) ]]; then : ; else mkdir -p $(wdir); fi"

define end =
	cat > $p/$@
endef

t: xmlIn
	$(begin) | $(end)

xmlIn: ex.xml
	$(init) && cat $^ | $(end)

.PHONY: clean
clean:
	$(begin)
	rm -rf $p build $p/{xmlIn,t} deps.dot deps.png

# ( cd build && ls | jqRs 'map(select(test("^(util.mk|Makefile|ex.xml)$$") | not)) | .[] | "rm build/\(.)"' | jq -r '.' ) | bash.safe -x
#	cd build && find . -type f | grep -Ev '^\./(Makefile|ex.xml)$$' | xargs -r rm 
# rm -f deps.dot deps.png
