FROM eclipse-temurin:8-jre-alpine

LABEL maintainer="cp_energy_xxl_job"

RUN mkdir -p /zhurong-ems/xxljob/logs
RUN mkdir -p /zhurong-ems/xxljob
RUN mkdir -p /zhurong-ems/xxljob/temp

WORKDIR /zhurong-ems/xxljob

ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

EXPOSE 9100

ADD ./target/zhurong-ems-xxl-job-admin.jar ./app.jar

ENTRYPOINT ["java","-Dspring.profiles.active=prod", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
