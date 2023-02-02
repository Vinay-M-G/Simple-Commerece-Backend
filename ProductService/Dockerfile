FROM maven:3.8.4-openjdk AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml -DskipTests=true  package

#package
FROM public.ecr.aws/amazoncorretto/amazoncorretto:17
EXPOSE 2001
COPY --from=build home/app/target/ProductService-0.0.1-SNAPSHOT.jar /home/app/ProductService.jar 
ENTRYPOINT java -jar /home/app/ProductService.jar