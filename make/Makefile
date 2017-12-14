#!/usr/bin/make -f

include mkutil/begin

completion/dyna.mk: mkutil
	$(begin)
	$(MAKE) -C $(subst .mk,,$@)

mkutil: $(HOME)/deploy-tools.msginjector/bin/mkutil $(MAKEFILE_LIST) 
	$(begin)
	chmod u+w   $@
	rm -rf      $@
	cp -r $<    $@
	chmod uog-w $@
	git add     $@
	git commit -m 'mkutil update'
