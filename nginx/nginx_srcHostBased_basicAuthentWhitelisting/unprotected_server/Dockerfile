FROM node:9.8.0-alpine
RUN apk update && apk add ngrep
RUN npm add -g http-server
EXPOSE 8080
COPY index.html .
RUN ifconfig

CMD ngrep -d eth0 -q HTTP port 8080 & http-server



