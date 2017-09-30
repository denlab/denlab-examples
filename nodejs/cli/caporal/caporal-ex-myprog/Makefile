define end =
touch $@
echo $@ >> buildfiles
endef

.PHONY: all
all: run
	l
	chmod +x myprog.js

.PHONY: run
run: node_modules/caporal
	./myprog.js

compl: node_modules/caporal
	echo -e '\n# To add auto completion run:\nexport PATH=$$PATH:$$(pwd) && . <(./myprog.js completion $$(basename $$SHELL))\n'
	echo -e '# (zsh) to make it persist: '
	echo -e 'echo "source <(./myprog.js completion zsh)" > ~/.zsh/completion/_myprog.js  \
&& source ~/.zsh/completion/_myprog.js'
	$(end)

node_modules/caporal:
	npm install caporal --save

clean:
	cat buildfiles | sort | uniq | xargs -r rm 
	rm -f buildfiles
