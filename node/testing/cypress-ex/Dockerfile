FROM cypress/browsers
# COPY cypress-test /
RUN mkdir /cypress-test
WORKDIR cypress-test
RUN npm i -g npx
RUN npm init -y && npm i --save-dev cypress
RUN npx cypress verify
# RUN cat package.json 1>&2
# COPY cypress-test/cypress           /cypress-test/cypress
# COPY cypress-test/cypress.json      /cypress-test/
# COPY cypress-test/package.json      /cypress-test/
# COPY cypress-test/package-lock.json /cypress-test/
# RUN cat package.json 1>&2
# RUN npm install
SHELL ["bash","-xveuo","pipefail","-c"]
# RUN cd cypress && ls -alrth && ln -fs /cypress-videos videos && ls -alrth 
# CMD npm install && npx cypress run
CMD npx cypress run
