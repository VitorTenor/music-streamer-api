#FROM openjdk:17-jdk-alpine
#
#WORKDIR /app
#
#CMD ["mvn", "clean", "package"]
#
#COPY app/target/app-0.0.1-SNAPSHOT.jar ./app.jar
#COPY domain/target/domain-0.0.1-SNAPSHOT.jar ./domain.jar
#
#RUN chown tomcat:tomcat /projetos -R
#RUN chmod +x /app -R
#
FROM openjdk:17-jdk-alpine

# Instalação do Maven (caso necessário)
RUN apk add --no-cache maven

WORKDIR /app

# Copia os arquivos JAR para o contêiner
COPY app/target/app-0.0.1-SNAPSHOT.jar ./app.jar
COPY domain/target/domain-0.0.1-SNAPSHOT.jar ./domain.jar

CMD ["java", "-jar", "app.jar"]
