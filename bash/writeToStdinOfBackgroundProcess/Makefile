include mkutil/bash
include mkutil/begin

cmd :=
d := proc
$(shell mkdir -p $(d))

# --------------------------------------------------------------------------------

bg: $(cmd).check $d/$(cmd).in $d/$(cmd).in.pid
	$(begin)
	echo cmd=$(cmd)
	cat $d/$(cmd).in | $(cmd) &

$d/$(cmd).in:
	$(begin)
	mkfifo $@

$d/$(cmd).in.pid: $d/$(cmd).in
	$(begin)
	cat > $< & echo $$! > $@

$(cmd).check:
	$(begin)
	$(if $(cmd),,echo $$'\nError!\n- variable cmd must be defined!' && exit 1)

info: cmd 
	export cmd='$(shell cat $<)' \
	&& echo "- cmd=$${cmd}" \
	&& if [[ -f $${cmd}.input.pid ]]; then \
	     echo "- cmd.in.pid file: exists" \
	     && if pgrep $$(cat $${cmd}.input.pid); then \
	       echo '- process is alive' ; \
	     else \
	       echo '- process is dead' ; \
	     fi ; \
	   else \
	     echo "- cmd.in.pid file: do not exists"; \
	   fi



# bash -c 'cat serv-info/serv-input-cat-pid | tee >(cat 1>&2) | x echo "ps f {}" | grep -C3 '




srv-info: 
	mkdir serv-info
	mkfifo serv-info/srv-input
	cat > serv-info/srv-input & 
	echo $! > serv-info/serv-input-cat-pid
	cat serv-info/srv-input | sed 's/.*/changed! &/' & 
  

.PHONY: clean info

clean:
	rm -rf serv-info


