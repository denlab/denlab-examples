.PHONY: deploy check-env
	echo $@

deploy: check-env
	echo $@

other-thing-that-needs-env: check-env
	echo $@

# define assert_varisdef
$(info - cmd=$(cmd))
check-env:
ifndef cmd
	$(error cmd is undefined)
endif
