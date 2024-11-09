FROM openjdk:17
LABEL MAINTAINER="Ambrish"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 9091
ENTRYPOINT ["java",\
"-verbose:gc -XX:+PrintGCTimeStamps",\
"-XX:+PrintGCDetails",\
"-Xloggc:./GarabgeCollection-%t.log",\
"-XX:+UseStringDeduplication",\
"-XX:+UseParallelGC",\
"-Xms256m",\
"-Xmx512m",\
"-Dspring.profiles.active=dev",\
"-jar","/app.jar"]