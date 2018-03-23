cnt := cypressex
img := cypressex
vid := cypress-test/cypress/videos

$(shell mkdir -p build)

show: run
	google-chrome $(vid)/$(shell comm -13 build/before.lst build/after.lst)

run:
	ls $(vid) > build/before.lst
	dc build && dc run cypress
	ls $(vid) > build/after.lst

run_sh:
	dc build && dc run cypress sh
