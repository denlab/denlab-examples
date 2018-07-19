SHELL       := /bin/bash
.SHELLFLAGS := -euo pipefail -c

d := build
$(shell mkdir -p $d)

.PHONY: browse
browse: deploy
	xdg-open http://`docker port tomcat 8080`/myapp

.PHONY: deploy
deploy: $d/myapp/target/myapp.war
	docker rm -f tomcat || :
	docker run -d --rm --name tomcat -p 8080 tomcat:alpine
	docker exec tomcat sh -xv -euo pipefail -c 'apk update && apk add findutils'
	docker exec tomcat find / -name webapps | tee build/webappPath
	docker cp $< tomcat:`cat build/webappPath`
	wait-for-expr.sh -c 'bash -c '\''docker logs tomcat |& egrep "Deployment of.*myapp.*has finished"'\''' 


.PHONY: build
build: $d/myapp/target/myapp.war
	@:

$d/myapp/target/myapp.war: $d/myapp
	cd $< && mvn package

$d/myapp:
	cd $d && mvn archetype:generate									\
		-DgroupId=com.mycompany.app										\
	  -DartifactId=myapp														\
	  -DarchetypeArtifactId=maven-archetype-webapp	\
	  -DinteractiveMode=false
	sed -r '$$ d' -i ./build/myapp/src/main/webapp/WEB-INF/web.xml
	echo '<listener><listener-class>myapp.Listener</listener-class></listener>' >> ./build/myapp/src/main/webapp/WEB-INF/web.xml
	echo '</web-app>' >> ./build/myapp/src/main/webapp/WEB-INF/web.xml
	d=build/myapp/src/main/java \
		&& mkdir -p $$d \
		&& cp src/myapp/Listener.java $$d
	 sed -r 's|</dependencies>|<dependency> <groupId>javax.servlet</groupId> <artifactId>javax.servlet-api</artifactId> <version>4.0.1</version> <scope>provided</scope></dependency></dependencies>|' -i build/myapp/pom.xml

.PHONY: clean
clean:
	rm -rf $d



