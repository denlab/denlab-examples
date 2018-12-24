FROM centos:7 
# RUN apk update && apk add ca-certificates
RUN yum install -y ca-certificates
RUN yum install -y wget
RUN wget https://github.com/wagoodman/bashful/releases/download/v0.0.10/bashful_0.0.10_linux_amd64.rpm \
    && rpm -i bashful_0.0.10_linux_amd64.rpm
RUN echo $'tasks:\n\
- cmd: echo "Hello, World!"\n\
' > task
RUN bashful run task


