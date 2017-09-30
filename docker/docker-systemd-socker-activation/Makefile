ngPort := 1080
ngName := ng$(ngPort)

serviceAll := nginx-proxy.socket nginx-proxy.service nginx-docker.service
# cont.logs:
# 	docker.ps.names

statusAll:
	echo $(serviceAll) | xargs -n1 sudo systemctl --no-pager--no-pager  status 

sys.logs:
	 sudo journalctl -xe

cont.logs:
	docker logs -f $(ngName)

net.check:
	curl http://localhost:80

.PHONY: net.status
net.status:
	sudo fuser -n tcp 80	

.PHONY: nginx-proxy.socket.stop
nginx-proxy.socket.stop:
	sudo systemctl stop nginx-proxy.socket

.PHONY: nginx-proxy.socket.status
nginx-proxy.socket.status:
	sudo systemctl status nginx-proxy.socket

.PHONY: nginx-proxy.socket.start
nginx-proxy.socket.start:
	sudo systemctl start nginx-proxy.socket

.PHONY: nginx-proxy.socket.enable
nginx-proxy.socket.enable:
	sudo systemctl enable nginx-proxy.socket

.PHONY: nginx-proxy.socket.disable
nginx-proxy.socket.disable:
	sudo systemctl disable nginx-proxy.socket

install.unit.socket: /etc/systemd/system/nginx-proxy.socket;
install.unit.service: /etc/systemd/system/nginx-proxy.service;
install.unit.docker-service: /etc/systemd/system/nginx-docker.service;

/etc/systemd/system/nginx-docker.service: nginx-docker.service
	sudo cp $^ $@

/etc/systemd/system/nginx-proxy.socket: nginx-proxy.socket
	sudo cp $^ $@

/etc/systemd/system/nginx-proxy.service: nginx-proxy.service
	sudo cp $^ $@

docker.ps:
	docker ps -a | grep --color=always ng

cont:
	if ! docker ps | grep -q $(ngName); then docker create --name $(ngName) -p $(ngPort):80 nginx ; fi

clean:
	docker rm $(ngName)
