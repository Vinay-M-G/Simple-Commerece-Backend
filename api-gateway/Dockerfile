FROM maven:3.8.4-openjdk AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml -DskipTests=true  package

# Package stage
FROM public.ecr.aws/amazoncorretto/amazoncorretto:17
EXPOSE 9001
COPY --from=build home/app/target/api-gateway-0.0.1-SNAPSHOT.jar /home/app/api-gateway.jar 
ENTRYPOINT java -jar /home/app/api-gateway.jar