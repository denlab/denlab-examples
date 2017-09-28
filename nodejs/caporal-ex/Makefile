b := build

.PHONY: $b/all
$b/all: $b/run
	l
	chmod +x myprog.js

$b/run: $b/npm.add.caporal
	./myprog.js

$b/compl: $b/npm.add.caporal
	echo -e '\n# To add auto completion run:\nexport PATH=$$PATH:$$(pwd) && . <(./myprog.js completion $$(basename $$SHELL))\n'
	echo -e '# (zsh) to make it persist: '
	echo -e 'echo "source <(./myprog.js completion zsh)" > ~/.zsh/completion/_myprog.js  \
&& source ~/.zsh/completion/_myprog.js'
	touch $@

$b/npm.add.caporal: $b/init
	npm install caporal --save
	touch $@

$b/init:
	mkdir -p $b

clean:
	rm -rf build

