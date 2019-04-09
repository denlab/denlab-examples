FROM alpine:3.7

RUN apk update && apk add alpine-sdk wget gettext-dev autoconf automake readline-dev

ENV d=/repos/github.com/rocky
RUN mkdir -p $d
WORKDIR $d
RUN git clone https://github.com/rocky/remake.git
WORKDIR $d/remake
RUN git checkout e3e34dd
RUN autoreconf -f -i
RUN ./configure
RUN make po-update
RUN make
RUN PERL_USE_UNSAFE_INC=1 make check
RUN make install
