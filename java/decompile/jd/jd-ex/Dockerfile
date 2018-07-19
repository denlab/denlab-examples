FROM clojure:alpine

RUN cd / && lein new app jd-ex
WORKDIR /jd-ex
COPY project.clj .
RUN cat project.clj
RUN lein deps
COPY C.java .
RUN javac C.java
RUN java C
RUN mkdir decompile && cp C.class decompile
WORKDIR decompile
RUN mkdir d
RUN jar cvf C.jar C.class 
CMD set -xv && ls -alrth && java -jar $(find ~/.m2 -iname '*jd*core*.jar') C.jar . && cat C.java
 



