#!/usr/bin/make -f

include mkutil/bash
dockervirt_path := ~/repos/github.com/riggerthegeek/docker-virtual-host
# <targets ---------------------------------------------------------------------

docker-virtual-host: $(dockervirt_path)
	cp -r $< $@ && touch $@

$(dockervirt_path):
	git.repo.clone <<< https://github.com/riggerthegeek/docker-virtual-host.git && touch $@

# </targets --------------------------------------------------------------------
