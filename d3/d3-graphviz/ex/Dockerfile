FROM alpine:3.9.2
RUN apk update
RUN apk -U add npm git
RUN npm i -g http-server
RUN git clone https://github.com/magjac/d3-graphviz.git
WORKDIR d3-graphviz
RUN npm i
RUN npm run build
EXPOSE 8080
# COPY index.html .  
CMD http-server  -p 8080

