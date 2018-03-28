include mkutil/bash
$(shell mkdir -p build)

up:
	test -f bg && (kill -9 $$(cat build/bg) && rm build/bg ) || :
	dc up --build client
	dc logs -f & echo $$! > build/bg

down:
	test -f bg && (kill -9 $$(cat bg) ) || :
	rm -f build/bg
	dc kill client server
	dc down --remove-orphans
	rm -rf build
