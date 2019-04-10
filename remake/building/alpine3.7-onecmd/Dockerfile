FROM alpine:3.7

RUN set -xv                                                                \
  && deps='wget alpine-sdk gettext-dev autoconf automake readline-dev git' \
  && apk -U add $deps                                                      \
  && d=/repos/github.com/rocky                                             \
  && mkdir -p $d                                                           \
  && cd $d                                                                 \
  && ls -alrth                                                             \
  && git clone https://github.com/rocky/remake.git                         \
  && cd $d/remake                                                          \
  && git checkout e3e34dd                                                  \
  && autoreconf -f -i                                                      \
  && ./configure                                                           \
  && make po-update                                                        \
  && make                                                                  \
  && PERL_USE_UNSAFE_INC=1 make check                                      \
  && make install                                                          \
  && apk del $deps
