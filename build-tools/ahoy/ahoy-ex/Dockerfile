FROM alpine:3.8
RUN apk update && apk add ca-certificates wget curl
RUN apk add bash 
RUN apk add util-linux
RUN apk add coreutils
RUN wget -q https://github.com/ahoy-cli/ahoy/releases/download/2.0.0/ahoy-bin-`uname -s`-amd64 -O /usr/local/bin/ahoy \
    && chmod +x /usr/local/bin/ahoy
COPY tasks.yaml /
RUN mkdir /ahoy-ex
WORKDIR /ahoy-ex
RUN ahoy init
RUN cat .ahoy.yml
