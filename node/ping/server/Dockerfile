FROM node:alpine
COPY server /server
WORKDIR /server
RUN ls -alrth
RUN npm install
CMD ["node","server.js"]
EXPOSE 80
