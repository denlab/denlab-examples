include mkutil/begin
include mkutil/bash

gorilla-new = if ! [[ -d $@ ]]; then lein new gorilla-app $@; fi

all: lein-new-gorilla-app gorilla-spec/project.clj

lein-new-gorilla-app:
	$(begin)
	$(gorilla-new)	

gorilla-spec/project.clj: gorilla-spec
	$(begin)
	sed -r 's|org.clojure/clojure "1.8.0"|org.clojure/clojure "1.9.0"|' -i $@

gorilla-spec:
	$(begin)
	$(gorilla-new)	

