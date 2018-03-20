FROM alpine
RUN apk update && apk add bash coreutils curl grep ngrep bind-tools
# sshd
RUN apk update && apk add openssh-server openssh-keygen
RUN apk update && apk add openssh-client
RUN ssh-keygen -A
# ssh without passwords

# COPY wait-for-it.sh /usr/local/bin
# COPY entrypoint.sh /usr/local/bin

CMD passwd -d root                                                                                         \
    && sed -r                                                                                              \
        -e 's/#(PermitEmptyPasswords) no/\1 yes/'                                                          \
        -e 's/#(PasswordAuthentication yes)/\1/'                                                           \
        -e 's/#(PermitRootLogin).*$/\1 yes/'                                                               \
        -i /etc/ssh/sshd_config                                                                            \
    && egrep 'AllowUsers|PermitRootLogin|PermitEmptyPasswords|PasswordAuthentication' /etc/ssh/sshd_config \
    && /usr/sbin/sshd -D
