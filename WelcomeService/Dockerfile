FROM maven:3.8.4-openjdk AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml -DskipTests=true  package

# Package stage
FROM public.ecr.aws/amazoncorretto/amazoncorretto:17
EXPOSE 1001
COPY --from=build home/app/target/WelcomeService-0.0.1-SNAPSHOT.jar /home/app/WelcomeService.jar 
ENTRYPOINT java -jar /home/app/WelcomeService.jar