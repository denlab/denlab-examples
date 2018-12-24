include mkutil/bash
include mkutil/begin
content := '																						\
	["LANG=en_US.utf-8", "LC_ALL=en_US.utf-8"] as $$vars	\
| debug | if contains($$vars)|debug then "echo nothingtodo" else ($$vars|join("\n")|"echo \"\(.)\"| tee -a /etc/environment")		end				\
'
.PHONY: all
all: multiline_assert
	$(begin)
	@:

multiline_assert: multiline_expected multiline_actual
	$(begin)
	diff <(cat $<) <(cat $(lastword $^))

multiline_actual:
	$(begin)
	echo $$'LANG=en_US.utf-8\n'\
	'LC_ALL=en_US.utf-8' | tee >(t.color_ 128 '.*' 1>&2) | jq -R . | jq -sr $(content) | $(end)

.PHONY: multiline_expected
multiline_expected:
	$(begin)
	echo 'echo nothingtodo' | tee $@

.PHONY: clean
clean:
	$(begin)
	rm -f multiline_* f tmp.cnt
	diff <(echo mkutil Makefile README.org | tr ' ' '\n'| sort) <(ls | sort)
