FROM alpine

RUN apk update && apk add opam
RUN apk update && apk add alpine-sdk
RUN apk update && apk add m4 rsync bash
# RUN apk make --version
# RUN exit 1
RUN opam init
RUN opam update
RUN opam install depext
RUN opam depext google-drive-ocamlfuse
RUN opam install google-drive-ocamlfuse
RUN apk update && apk add findutils
ENV PATH $PATH:/root/.opam/system/bin
# CMD google-drive-ocamlfuse -id $gdocf_clientId -secret $gdocf_secret
CMD echo google-drive-ocamlfuse -headless -id ${gdocf_clientId} -secret ${gdocf_secret}


