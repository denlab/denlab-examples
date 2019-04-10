img := $(shell readlink -f . | xargs basename | jq -Rr '"remake-\(.)"')
.PHONY: build
build:
	docker build . -t $(img) 

alpineImgSize:
	docker history alpine:3.7

totImgSize: build
	docker history t --no-trunc																				\
	| pp jql -R 'text::splits_blank[-1]'																	\
	| jql -s '.[1:] | map(units::str2bytes) | add | units::bytes2str' \
  | tee $@
