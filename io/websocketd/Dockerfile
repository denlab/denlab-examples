FROM alpine

RUN apk update && apk add curl bash
RUN apk update && apk add file 

WORKDIR /bin

RUN u=https://github.com/joewalnes/websocketd/releases/download/v0.2.12/websocketd-0.2.12-linux_amd64.zip && f=$(basename $u) && curl -L $u > $f && unzip $f && rm $f

COPY count.sh .  

RUN ls | grep web 
RUN ls | grep count 


RUN echo $PATH 
RUN ls -alrth /bin  | grep count
RUN /bin/websocketd --port=8080 /bin/count.sh
EXPOSE 8080
