# <variables> --------------------------------------------------------------------------------

SHELL       := /bin/bash
.SHELLFLAGS := -euo pipefail -c
d := build
$(shell set -xv && mkdir -p $d)
chroot_script_url := https://gist.githubusercontent.com/schnell18/7287068/raw/ec034572dd6c2d8af017060cf851b26c76049dae/create_chrootjail.sh
chroot_script     := $(notdir $(chroot_script_url))
CHROOT           := $d/chroot
# </variables> -------------------------------------------------------------------------------

# <targets> --------------------------------------------------------------------------------
all: $d/chroot $d/$(chroot_script)
	tree build
	du -sh build
	du -sh build/*

$d/chroot.tar.xz: $(shell find build/chroot -type f)
	cd $d && tar cavf chroot.tar.xz chroot

.PHONY: chroot_run
chroot_run: $d/chroot
	chroot build/chroot

$d/chroot: Makefile
	cp --parents /lib/ld-linux.so.2 /lib64/ld-linux-x86-64.so.2 `which bash` $@
	ldd `which bash` | grep -v dynamic | cut -d " " -f 3 | sed 's/://'|sort|uniq | jqRs 'map(select(length>0))'
	lib/sys.cp.withLibs ls cp rm cat mkdir ln grep cut sed vim ssh head tail which id find xargs git
	mkdir -p $(CHROOT)/{dev,etc,home,tmp,proc,root,var}
	: setup device
	echo 'cd $(PWD) && mknod $(CHROOT)/dev/null c 1 3'						>> build/runasroot
	echo 'cd $(PWD) && mknod $(CHROOT)/dev/zero c 1 5'						>> build/runasroot
	echo 'cd $(PWD) && mknod $(CHROOT)/dev/tty  c 5 0'						>> build/runasroot
	echo 'cd $(PWD) && mknod $(CHROOT)/dev/random c 1 8'					>> build/runasroot
	echo 'cd $(PWD) && mknod $(CHROOT)/dev/urandom c 1 9'					>> build/runasroot
	echo 'cd $(PWD) && chmod 0666 $(CHROOT)/dev/{null,tty,zero}'	>> build/runasroot
	echo 'cd $(PWD) && chown root.tty $(CHROOT)/dev/tty'					>> build/runasroot
	echo 'cp -r --parents /usr/share/git-core $CHROOT'
# copy vim resource files
	echo 'cp -r --parents /usr/share/vim $CHROOT'									>> build/runasroot
# copy basic system level files
	echo 'cp --parents /etc/group $CHROOT'												>> build/runasroot
	echo 'cp --parents /etc/passwd $CHROOT'												>> build/runasroot
	echo 'cp --parents /etc/nsswitch.conf $CHROOT'								>> build/runasroot
	echo 'cp --parents /etc/resolv.conf $CHROOT'									>> build/runasroot
	echo 'cp --parents /etc/hosts $CHROOT'												>> build/runasroot
	echo 'cp --parents /lib/libnss_* $CHROOT'											>> build/runasroot
	echo 'cp -r --parents /usr/share/terminfo $CHROOT'						>> build/runasroot

#echo 'cp --parents /etc/shadow $CHROOT' >> build/runasroot


$d/$(chroot_script):
	cd $(dir $@) && wget $(chroot_script_url)
	chmod +x $@

.PHONY: up
up: service									#: start all services
	docker-compose up -d --build
	docker-compose logs -f

.PHONY: down
down: service								#: stop all services
	docker-compose down "$@"
	docker-compose logs -f

.PHONY: run
run_shell: service					#: run a interactive shell
	docker-compose build
	docker-compose run $(shell cat $<) sh

.PHONY: run
run: service								#: run the unique service of this docker-compose
	docker-compose build
	docker-compose run $(shell cat $<)

service: docker-compose.yml	#: create the file containing the name of the service
	docker-compose config | sed -rn '/services:/,$$ p' | head -n 2 | tail -1 | tr -d ' :' | tee $@

.PHONY: clean
clean:											#: remove build files
	rm -rf service build

# </targets> -------------------------------------------------------------------------------
