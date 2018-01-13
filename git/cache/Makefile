include mkutil/bash

d != mktemp -d


cache_path := $(HOME)/.gitcaches/reference
curr_repo  != echo "file://$$(git rev-parse --show-toplevel)"
curr_repo_dir := $(notdir $(curr_repo))
cache_curr_remote :=  $(cache_path)/refs/remotes/denlab_denlab-examples
with_cache := with_cache/$(curr_repo_dir)
no_cache := no_cache/$(curr_repo_dir)

$(info - curr_repo_dir=$(curr_repo_dir))
$(shell mkdir -p $(cache_path))


all: $(no_cache)  $(with_cache)
	@:

$(cache_path):
	mkdir -p $@ \
	&& cd $@ \
	&& git init --bare 

$(cache_curr_remote):
	cd $(cache_path) \
	&& git remote add denlab_denlab-examples  $(curr_repo) \
	&& git fetch --all

$(with_cache): git_url $(cache_curr_remote)
	/usr/bin/time																	\
	bash -xeuo pipefail -c \
  "{																							\
	  cd $(dir $@) && git clone --reference $(cache_path) $(shell cat $<) ; \
	}"																							\
	|& grep elapsed | jq -Rr 'capture(" (?<elapsed>.:.....)elapsed ").elapsed' | tee $@

$(no_cache): git_url
	/usr/bin/time																	\
	bash -xeuo pipefail -c \
  "{																							\
	  cd $(dir $@) && git clone $(shell cat $<) ; \
	}"																							\
	|& grep elapsed | jq -Rr 'capture(" (?<elapsed>.:.....)elapsed ").elapsed' | tee $@

git_url:
	git remote -v | jq -R . | jq -rs '.[-1] | ltrimstr("origin\t") | split(" ")[0]' | tee $@

git_url:
	echo "file://$$(git rev-parse --show-toplevel)" | tee $@

clean:
	cat .gitignore | xargs rm -rf 
