FROM alpine
RUN apk update && apk add openssh-server openssh-keygen
RUN apk update && apk add openssh-client
RUN apk update && apk add curl
COPY files/etc/ssh /etc/ssh
RUN find /etc/ssh -name '*key*' -exec chmod 600 {} \;
RUN passwd -d root                             \
    && sed -r                                     \
        -e 's/#(PermitEmptyPasswords) no/\1 yes/' \
        -e 's/#(PasswordAuthentication yes)/\1/'  \
        -e 's/#(PermitRootLogin).*$/\1 yes/'      \
        -i /etc/ssh/sshd_config                   \
    && mkdir -p ~/.ssh                            \
    && echo 'CheckHostIP    no' >> ~/.ssh/config  \
    && echo 'HashKnownHosts no' >> ~/.ssh/config 

ENTRYPOINT ["/usr/sbin/sshd","-D"]
# ENTRYPOINT /usr/sbin/sshd -D
