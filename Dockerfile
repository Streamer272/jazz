FROM gradle:jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

FROM openjdk:17
EXPOSE 8080:8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/main.jar
COPY run.sh /app/run.sh
COPY jazz-gae-proxy.json /app/jazz-gae-proxy.json
RUN ["curl", "-o", "/app/cloud-sql-proxy", "https://storage.googleapis.com/cloud-sql-connectors/cloud-sql-proxy/v2.1.2/cloud-sql-proxy.linux.amd64"]
RUN ["chmod", "+x", "/app/run.sh"]
RUN ["chmod", "+x", "/app/cloud-sql-proxy"]
ENTRYPOINT ["/app/run.sh"]
