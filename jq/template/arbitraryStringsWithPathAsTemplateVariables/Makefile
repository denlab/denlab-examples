all: 
	jq -n --argfile args args.json --argfile template template.json --argfile templatemap templatemap.json -f fillin.jq
	# jq --argfile args args.json -f fillin.jq template.json
