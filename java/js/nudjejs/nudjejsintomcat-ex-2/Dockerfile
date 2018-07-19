FROM maven:alpine AS mvn
RUN mkdir -p /webapp
WORKDIR /webapp
# COPY src .
RUN mvn archetype:generate                   \
-DgroupId=com.mycompany.app									 \
-DartifactId=myapp													 \
-DarchetypeArtifactId=maven-archetype-webapp \
-DinteractiveMode=false
WORKDIR /webapp/myapp
 

FROM tomcat:alpine
