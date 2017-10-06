
info:
	bash -c 'cat serv-info/serv-input-cat-pid | tee >(cat 1>&2) | x echo "ps f {}" | grep -C3 '


srv-info: 
	mkdir serv-info
	mkfifo serv-info/srv-input
	cat > serv-info/srv-input & 
	echo $! > serv-info/serv-input-cat-pid
	cat serv-info/srv-input | sed 's/.*/changed! &/' & 
  

.PHONY: clean info

clean:
	rm -rf serv-info


