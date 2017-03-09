btrace-bin/samples/AllMethods2.java: btrace-bin
	cp AllMethods2.java btrace-bin/samples/

btrace-bin: btrace-bin.tar.gz
	mkdir btrace-bin && cp btrace-bin.tar.gz btrace-bin && cd btrace-bin && tar xaf btrace-bin.tar.gz && chmod +x bin/*

btrace-bin.tar.gz.sha256sum: btrace-bin.tar.gz
	sha256sum build/btrace-bin.tar.gz > btrace-bin.tar.gz.sha256sum 

btrace-bin.tar.gz: 
	wget https://kenai.com/projects/btrace/downloads/download/releases/release-1.2.5.1/btrace-bin.tar.gz

clean:
	rm -rf btrace-bin btrace-bin.tar.gz

