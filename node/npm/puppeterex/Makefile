include mkutil/bash

d := build

$(shell mkdir -p $d)

# <targets> ------------------------------------

$d/start:
	{ unbuffer /opt/google/chrome-beta/chrome --remote-debugging-port=9222 ; } &> $@ & 

$d/ws: $d/start
	jq -Rr 'capture(".* (?<ws>ws://[^ ]+)( .*|$$)").ws' $< > $@

killall:
	pkill chrome

search: $d/ws
	 ws='$(shell cat $<)' node search.js
