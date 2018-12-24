FROM alpine:3.6

RUN apk update && apk add go
RUN apk add curl git
RUN apk add libc-dev
RUN go get github.com/wagoodman/bashful
RUN apk add findutils
RUN find / -name bashful -executable -type f
RUN cd /usr/local/bin/ && ln -s /root/go/bin/bashful
RUN apk search brew |  tee /out
# RUN echo $'tasks:\n\
# - cmd: echo "Hello, World!"\n\
# ' > task
# RUN bashful run task

