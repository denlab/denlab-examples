FROM alpine
RUN apk update && apk add curl bash coreutils jq
COPY wait-for-it.sh /usr/local/bin
COPY entrypoint.sh /usr/local/bin
CMD ["wait-for-it.sh","-s","-h","-p","server:80","--","entrypoint.sh"]
