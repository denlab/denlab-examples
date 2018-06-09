FROM openstf/stf 
USER root
RUN usermod -aG plugdev stf
RUN whoami
RUN apt update && apt install -y adb
RUN adb version
RUN apt update && apt install -y usbutils && lsusb --version
USER stf

CMD ["stf","local"]
