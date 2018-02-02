FROM alpine
RUN apk update && apk add bash
RUN p=/usr/local/bin/websocketd \
    && wget https://github.com/joewalnes/websocketd/releases/download/v0.3.0/websocketd-0.3.0-linux_amd64.zip -O /zip
RUN p=/usr/local/bin/websocketd \
    && unzip /zip 
RUN mv /websocketd /usr/local/bin
RUN chmod +x /usr/local/bin/websocketd
COPY count.sh /usr/local/bin
EXPOSE 8080
CMD websocketd --port=8080 count.sh

