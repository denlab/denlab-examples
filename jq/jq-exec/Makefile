.PHONY: common $(all)

all=j-w-stdin.ex j-without-stdin.ex

all: $(all)

j-w-stdin.ex: common
	seq 3 | ./j-with-stdin

j-without-stdin.ex: common
	arg=3 ./j-without-stdin


