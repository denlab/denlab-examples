SHELL       := /bin/bash
.SHELLFLAGS := -euo pipefail -c
 
d := build
$(shell mkdir -p $d)
$(shell if docker.ps.names | egrep -q '^tomcat$$'; then \
          if [[ -f $d/deploy ]]; then									\
			      : ;																				\
          else																				\
            touch $d/deploy;													\
          fi;																					\
        else																					\
          rm -f $d/deploy;														\
        fi																						\
)

$d/deploy_test: $d/deploy
	curl -sS --fail -L http://$(shell docker port tomcat 8080)/myapp \
		| egrep 'Hello World'
	touch $@

$d/deploy: $d/myapp/target/myapp.war
	docker rm -f tomcat || :
	docker run -d --rm --name tomcat -p 8080 tomcat:alpine
	docker exec tomcat sh -xv -euo pipefail -c 'apk update && apk add findutils'
	docker exec tomcat find / -name webapps | tee build/webappPath
	docker cp $< tomcat:`cat build/webappPath`
	wait-for-expr.sh -c 'bash -c '\''docker logs tomcat |& egrep "Deployment of.*myapp.*has finished"'\''' 
	touch $@

.PHONY: browse
browse: $d/deploy
	xdg-open http://`docker port tomcat 8080`/myapp

.PHONY: build
build: $d/myapp/target/myapp.war
	@:

$d/myapp/target/myapp.war: $d/myapp
	cd $< && mvn package

$d/myapp: Makefile
	rm -rf $@
	cd $d && mvn archetype:generate																																					\
		-DgroupId=com.mycompany.app																																						\
	  -DartifactId=myapp																																										\
	  -DarchetypeArtifactId=maven-archetype-webapp																													\
	  -DinteractiveMode=false
	(																																																				\
		cat $d/myapp/src/main/webapp/WEB-INF/web.xml																													\
			| xmlToJson																																													\
			| jq '.'																																														\
		&& yamlToJson <<< $$'web-app: \n  listener: \n    listener-class: myapp.Listener'											\
	)																																																				\
	| jq -s 'first * last'																																									\
			| jsonToXml																																													\
			| xml.pretty	
	d=build/myapp/src/main/java																																							\
		&& mkdir -p $$d																																												\
		&& cp src/myapp/Listener.java $$d
	(																																																				\
		   cat $d/myapp/pom.xml        | xmlToJson																														\
		&& cat src/servlet.web.xml.json| yamlToJson																														\
	)																																																				\
	| jq -s '.[1].dependency as $$deps | .[0].project.dependencies.dependency |= ([.] + [$$deps])  | first'	\
		| jsonToXml | xml.pretty																																							\
	| sponge $d/myapp/pom.xml


.PHONY: clean
clean:
	rm -rf $d



