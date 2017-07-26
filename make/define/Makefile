define pInfo =
@jq -cn --arg at '$@' --arg caret '$^' '{"$$@": $$at,"$$^": $$caret}' 
endef


start: a b
	$(pInfo)

a:
	$(pInfo)

b: c d
	$(pInfo)

c:
	$(pInfo)

d:
	$(pInfo)
