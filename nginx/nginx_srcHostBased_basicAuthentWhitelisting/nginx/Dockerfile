FROM nginx:1.13.9-alpine

RUN apk update && apk add ngrep
RUN apk update && apk add bash grep 
# <passwordless openssh>
RUN apk update && apk add openssh-server openssh-keygen
COPY files/etc/ssh /etc/ssh
RUN find /etc/ssh -name '*key*' -exec chmod 600 {} \;
RUN passwd -d root                        \
&& sed -r                                 \
-e 's/#(PermitEmptyPasswords) no/\1 yes/' \
-e 's/#(PasswordAuthentication yes)/\1/'  \
-e 's/#(PermitRootLogin).*$/\1 yes/'      \
-i /etc/ssh/sshd_config
# </passwordless openssh>

RUN apk update && apk add bind-tools

COPY default.conf /etc/nginx/conf.d/default.conf
# COPY default.conf /etc/nginx/conf.d/protected_server.conf

EXPOSE 80

# ENTRYPOINT bash -c '/usr/bin/sshd -D & nginx -g "daemon off;"'
ENTRYPOINT /usr/sbin/sshd && nginx -g "daemon off;"
