SHELL       := /bin/bash
.SHELLFLAGS := -euo pipefail -c
$(shell mkdir -p build)

assert_logs: up
	@shutil/assert 'stage s1 was run' egrep -q "This is stage: stage1" build/log
	@shutil/assert 'stage s2 was run' egrep -q "This is stage: stage2" build/log

up:
	docker-compose up --build |& tee build/log

clean:
	rm -rf build
