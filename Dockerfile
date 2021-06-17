FROM openjdk:11
RUN  addgroup --gid 1000 spring || true
RUN  useradd -m -g spring --shell /bin/sh spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]