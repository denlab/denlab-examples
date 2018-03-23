FROM alpine
RUN apk update && apk add openssh-client bash coreutils
RUN apk add openssh-doc man
RUN apk add less grep
RUN apk update && apk add bind-tools jq
COPY wait-for-it.sh /usr/local/bin
ENV PAGER less -JSIR
COPY files/root/.ssh /root/.ssh
COPY nginx_whitelist /usr/local/bin

COPY files/usr/local/bin/common.sh      /usr/local/bin
COPY files/usr/local/bin/f.log.sh       /usr/local/bin/
COPY files/usr/local/bin/common.sh      /usr/local/bin/
COPY files/usr/local/bin/entrypoint.sh  /usr/local/bin/
COPY files/usr/local/bin/f.log.sh       /usr/local/bin/
COPY files/usr/local/bin/header.sh      /usr/local/bin/
COPY files/usr/local/bin/mod.sh         /usr/local/bin/
COPY files/usr/local/bin/sponge         /usr/local/bin/
COPY files/usr/local/bin/util.route.sh  /usr/local/bin/
COPY files/usr/local/bin/entrypoint.sh  /usr/local/bin/


# COPY header.sh mod.sh util.route.sh sponge f.log.sh /usr/local/bin/
RUN chmod 600 /root/.ssh/config
CMD entrypoint.sh
