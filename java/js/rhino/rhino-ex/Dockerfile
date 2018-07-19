FROM clojure:alpine
RUN apk update && apk add grep
SHELL ["bash","-euo","pipefail","-c"]
RUN cd / && lein new app jd-ex
WORKDIR /jd-ex
COPY project.clj .
RUN lein deps
RUN find ~/.m2 -name "*rhino*.jar" > /tmp/rhinoJarPath
WORKDIR /tmp
COPY Test.js .
CMD    echo '- compiling Test.js class ...' 1>&2                                  \
    && java -cp $(cat rhinoJarPath) org.mozilla.javascript.tools.jsc.Main Test.js \
    && echo '- running Test.class      ...' 1>&2                                  \
    && java -cp $(cat rhinoJarPath):. Test | egrep --color=always "hi, mom" 
