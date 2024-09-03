#1
FROM gradle:7.1.1-jdk11 AS BUILD_IMAGE

ARG project_path=.
RUN mkdir /apps
COPY --chown=gradle:gradle $project_path /apps
WORKDIR /apps

RUN gradle clean build

#2
FROM openjdk:11-jre
COPY --from=BUILD_IMAGE /apps/build/libs/be-report-0.0.1-SNAPSHOT.jar .
COPY $project_path/startup.sh .

CMD bash startup.sh
