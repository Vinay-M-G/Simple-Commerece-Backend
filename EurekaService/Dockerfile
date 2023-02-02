FROM maven:3.8.4-openjdk AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean install

# Package stage
FROM public.ecr.aws/amazoncorretto/amazoncorretto:17
EXPOSE 8761
COPY --from=build home/app/target/EurekaService-0.0.1-SNAPSHOT.jar /home/app/EurekaService.jar
ENTRYPOINT java -jar /home/app/EurekaService.jar
