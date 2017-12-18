FROM alpine:3.6

RUN apk update && apk add alpine-sdk

RUN git clone git://anonscm.debian.org/pkg-perl/packages/libhash-moreutils-perl.git

WORKDIR libhash-moreutils-perl

RUN apk update && apk add perl-utils gnupg

RUN apk update && apk add perl-module-build 

RUN perl  Build.PL

RUN make

# RUN perl Build.PL

