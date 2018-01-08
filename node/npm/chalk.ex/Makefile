include mkutil/bash

d := build
$(shell mkdir -p $(d))

repl2in:
	cat repl.js > $d/nodein

bg_in2node:
	cat $d/nodein | unbuffer -p node -i 

bg_in2node_file:
	cat $d/nodein | unbuffer -p node -i > $d/out 2> $d/err

bg_noeof:
	cat > $d/nodein

$d/nodein:
	mkfifo $@

clean:
	rm -rf $d
