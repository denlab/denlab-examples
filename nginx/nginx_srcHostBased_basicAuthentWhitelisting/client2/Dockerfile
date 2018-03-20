FROM alpine
RUN apk update && apk add bash coreutils curl grep ngrep 
COPY wait-for-it.sh /usr/local/bin
COPY entrypoint.sh /usr/local/bin
ENTRYPOINT ["entrypoint.sh"]

