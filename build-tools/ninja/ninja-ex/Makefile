all: clean ninja

ninja:
	ninja

clean:
	rm -rf *.o

#: remove built files + log, metadata etc
clean-all: clean
	rm -rf .ninja_log .ninja_deps build

