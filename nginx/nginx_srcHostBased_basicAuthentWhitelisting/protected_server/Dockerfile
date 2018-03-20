FROM nginx:1.13.9-alpine
RUN apk update && apk add openssl
COPY nginx.conf /etc/nginx/nginx.conf
RUN echo -n 'user:' >> /etc/nginx/.htpasswd
RUN echo 'pwd' | openssl passwd -apr1 -stdin >> /etc/nginx/.htpasswd
EXPOSE 80
