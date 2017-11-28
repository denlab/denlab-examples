#!/usr/bin/make -f

SHELL       := /bin/bash
.SHELLFLAGS := -euo pipefail -c

define begin =
	@echo $@ | GREP_COLOR="01;44" egrep --color=always ".*" 1>&2
endef

$(shell mkdir -p build)

all: clean build/realsize build/example/size 
	$(begin)

ls = find $(1) -mindepth 1 -maxdepth 1 -type $(2) 
ls_dirs = $(call ls,$(1),d)
ls_files = $(call ls,$(1),f)

build/example/size:
	$(begin)
	$(call ls_dirs,$(dir $@)) | jq -Rr  '"include \(.)/size.mk"' | jq -R '.' | jq -s '.'
	jq -nr '"$(dir $@)/filesSize: "'	
	$(call ls_files,$(dir $@)) | xargs ls -alrth | cut -d ' ' -f 5 | jq -s add 

build:
	$(begin)
	mkdir -p build

build/realsize: build/example 
	$(begin)
	find $< -type f -exec ls -alrth {} \; | cut -d' ' -f 5 | jq -s add |  tee $@

build/example: Makefile | build
	$(begin)
	mkdir -p $@
	echo > $@/1
	cat $@/1 $@/1 > $@/2 
	cat $@/2 $@/2 > $@/4
	mkdir $@/a
	cp $@/1 $@/a
	mkdir $@/a/a
	cp $@/1 $@/a/a/
	cp $@/2 $@/a/a/
	cp -r $@/a $@/b
	cp $@/2 $@/b
	cp $@/{2,4} $@/b/a/
	find $@ -type f -exec ls -alrth {} \; | sort -k 9 -s
	tree $@
	du -sh $@

.PHONY: clean
clean:
	$(begin)
	rm -rf build



