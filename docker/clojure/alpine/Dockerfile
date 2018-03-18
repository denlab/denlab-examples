FROM alpine

RUN apk update && apk add bash openjdk8
RUN cd /usr/local/bin && wget https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein && chmod +x lein && lein version
