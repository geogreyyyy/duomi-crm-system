FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER geogreyyyy@gmail.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

#RUN mkdir -p /jeecg-boot/config/jeecg/

WORKDIR /domi-crm-system

EXPOSE 8080

ADD jeecg-system-start-3.7.0.jar ./
ADD application-prod.yml ./

CMD java -Djava.security.egd=file:/prod/./urandom -jar jeecg-system-start-3.7.0.jar --spring.config.location=application-prod.yml