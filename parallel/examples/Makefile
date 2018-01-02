#!/usr/bin/make -f

include mkutil/bash

ext:
	echo $$'foo.x\nbar.y\nbaz.z'


PHONY: a
a:
	seq 3 | parallel echo "hi {}"
	seq 3 | parallel echo "hi ::::"


