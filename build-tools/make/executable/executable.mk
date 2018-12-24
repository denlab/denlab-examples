#!/usr/bin/make -f

include util.mk

foo: bar ## The foo target
	echo $@

bar: baz ## The bar target
	echo $@

baz: ## The baz target
	echo $@
