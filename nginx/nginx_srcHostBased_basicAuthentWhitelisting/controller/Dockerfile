FROM alpine
RUN apk update && apk add bash
SHELL ["bash","-xveuo","pipefail","-c"]
RUN apk update && apk add openssh-client bash coreutils sed 
ENV RQ https://s3-eu-west-1.amazonaws.com/record-query/record-query/x86_64-unknown-linux-musl/rq
RUN cd /usr/local/bin && wget ${RQ} && set +x rq && ls -alrth rq
RUN ls -alrth /usr/local/bin/rq && chmod +x /usr/local/bin/rq && ls -alrth /usr/local/bin/rq
RUN rq --version
RUN apk add file
RUN file /usr/local/bin/rq
# RUN /usr/local/bin/rq --version
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
COPY files/usr/local/bin/json.eval.path  /usr/local/bin/
COPY files/usr/local/bin/assert.sh  /usr/local/bin/
COPY files/usr/local/bin/with-tmpdir  /usr/local/bin/
COPY files/usr/local/bin/bash.safe  /usr/local/bin/
COPY files/usr/local/bin/jqRs /usr/local/bin/
COPY mk_hosts2ips /usr/local/bin/

RUN jq -n '{a: "seq 2"}' | json.eval.path '.a'


# COPY header.sh mod.sh util.route.sh sponge f.log.sh /usr/local/bin/
RUN chmod 600 /root/.ssh/config
CMD entrypoint.sh
