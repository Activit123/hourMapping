FROM amazoncorretto:21-alpine-jdk
COPY out/artifacts/Internship_2024_java_green_jar/Internship-2024-java-green.jar Internship-2024-java-green.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "Internship-2024-java-green.jar" ]