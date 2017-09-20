
showUtilHelp = false 
$(info showUtilHelp=$(showUtilHelp))
include util.mk

ranTargets = 

define jqEnd =
jq '{"$@": .}' > $@
endef

all: view

view: sumOneTwo ## view the results
	ls -rt | grep -E "($$(echo $(targetDb) | tr ' ' '|'))" | xargs cat | jq -s 'add' | jsonToYaml | ag --color-match 36 '\S+(?=:)' 

sumOneTwo: one two ## sum one and two
	cat $^ | jq -s '.' | jq 'map(.[]) | add' | $(jqEnd)

two: srcFile ## create two
	jq < srcFile '.[].two' | $(jqEnd)

one: srcFile ## create one
	jq < srcFile '.[].one' | $(jqEnd)

srcFile: ## create srcFile
	jq -n '{one: 1, two: 2}' | $(jqEnd)

.PHONY: view clean

clean: listTargets ## cleanup
	cat $^ | xargs rm -f
	rm $^
