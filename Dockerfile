# Usar a imagem oficial do Java
FROM openjdk:11-jdk-slim

# Definir o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copiar o arquivo jar da aplicação para o contêiner
COPY target/TriagemCheck-0.0.1-SNAPSHOT.jar /app/triagemcheck.jar

# Expor a porta da aplicação
EXPOSE 8082

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "triagemcheck.jar"]