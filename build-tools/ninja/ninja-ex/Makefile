all: clean ninja

ninja:
	ninja

clean:
	ninja -t clean
	rm -rf *.svg *.dot *.d bar foo special sum

#: remove built files + log, metadata etc
clean-all: clean
	rm -rf .ninja_log .ninja_deps build


oArc.tgz: bar.o
	tar cavf $@ $^

foo.o: foo.c

