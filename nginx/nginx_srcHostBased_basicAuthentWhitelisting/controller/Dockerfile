FROM alpine
RUN apk update && apk add openssh-client bash coreutils
RUN apk add openssh-doc man
RUN apk add less grep
RUN apk update && apk add bind-tools jq
COPY wait-for-it.sh /usr/local/bin
ENV PAGER less -JSIR
COPY common.sh     /usr/local/bin
COPY entrypoint.sh /usr/local/bin
COPY files/root/.ssh /root/.ssh
RUN chmod 600 /root/.ssh/config
CMD entrypoint.sh
