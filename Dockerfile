FROM amazoncorretto:21-alpine-jdk
COPY build/libs/Internship-2024-java-green-0.0.1-SNAPSHOT.jar Internship-2024-java-green-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java", "-jar", "Internship-2024-java-green-0.0.1-SNAPSHOT.jar" ]