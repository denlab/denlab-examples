FROM alpine

ENV NGINX_VERSION=1.1.13
RUN apk update && apk add alpine-sdk autoconf
RUN apk add pcre-dev
RUN apk add openssl-dev
RUN apk add linux-headers build-base
RUN apk add tar 
# RUN apk add libpcre3-dev libssl-dev libcurl4-openssl-dev
RUN git clone https://github.com/kare/ngx_http_auth_crowd_module.git
WORKDIR ngx_http_auth_crowd_module
RUN sed -r 's/sudo/:&/g' -i get-started.sh
RUN sed -r 's|curl -O http://|curl -O https://|g' -i get-started.sh
# RUN apk search autoreconf
# RUN autoreconf -f -i
# RUN ./configure --add-dynamic-module=../ngx_http_auth_crowd_module --with-debug --with-http_ssl_module

