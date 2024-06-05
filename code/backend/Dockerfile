FROM ubuntu:latest AS build
RUN apt-get update -y && apt-get upgrade -y
RUN apt-get install openjdk-21-jdk -y
RUN apt-get install -y maven
COPY . .
ENV URL=dpg-coon4nf79t8c73f6t5pg-a.oregon-postgres.render.com
ENV PORT=5432
ENV POSTGRES_DB=todolist_ge0p
ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=DD9YMNK4wILZRjnoNUsas0VRcivRs2iL
RUN mvn clean install -e
FROM openjdk:21-jdk-slim
EXPOSE 8080
COPY --from=build target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]