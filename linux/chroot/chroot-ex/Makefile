# <variables> --------------------------------------------------------------------------------
d := build
$(shell set -xv && mkdir -p $d)
chroot_script_url := https://gist.githubusercontent.com/schnell18/7287068/raw/ec034572dd6c2d8af017060cf851b26c76049dae/create_chrootjail.sh
chroot_script     := $(notdir $(chroot_script_url))

# </variables> -------------------------------------------------------------------------------

# <targets> --------------------------------------------------------------------------------
all: $d/chroot
	tree build

.PHONY: chroot_run
chroot_run: $d/chroot
	chroot build/chroot

$d/chroot: Makefile
	cp --parents /lib/ld-linux.so.2 /lib64/ld-linux-x86-64.so.2 `which bash` $@
	ldd `which bash` | grep -v dynamic | cut -d " " -f 3 | sed 's/://'|sort|uniq | jqRs 'map(select(length>0))'


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
