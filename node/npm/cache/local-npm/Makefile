sync: local-npm
	npm i -g local-npm
	cd local-npm && local-npm

$(HOME)/tmp/local-npm: 
	mkdir -p $@

local-npm: $(HOME)/tmp/local-npm
	ln -s $(HOME)/tmp/local-npm

