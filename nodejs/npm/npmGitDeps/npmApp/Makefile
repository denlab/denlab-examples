bin = $(abspath $(NODE_PATH)/../../bin/npmfornonnode)

.PHONY: all
all: $(bin);

t: 
	echo $(bin)
	file $(bin)

$(bin): npmfornonnode 
	echo $@
	npm install -g

