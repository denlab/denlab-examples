define pInfo =
	@echo '$@\n$^\n' | jq -R '.' | jq -s '. as [$$a,$$b] | {"$$@": $$a, "$$^": $$b}'
endef

start: a b
	$(pInfo)

a: b
	$(pInfo)

b:
	$(pInfo)
