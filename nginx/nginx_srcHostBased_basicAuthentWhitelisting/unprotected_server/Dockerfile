FROM node:9.8.0-alpine
COPY server /server
RUN apk update && apk add ngrep
RUN npm add -g http-server
EXPOSE 8080
COPY index.html .

WORKDIR /server
RUN npm install
# CMD ngrep -d eth0 -q HTTP port 8080 & http-server
CMD PORT=8080 node server.js
EXPOSE 8080



# CMD ["node","server.js"]

