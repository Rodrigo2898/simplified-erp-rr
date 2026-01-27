FROM maven:3.9.6-eclipse-temurin-21 AS builder
# define um diretório de trabalho dentro do container
WORKDIR /app
# Copia todos os arquivos do projeto para a máquina
COPY . .
# Executa o maven dentro do container
RUN mvn clean package -DskipTests

# imagem que vai rodar em prod - menor
FROM eclipse-temurin:21-jdk-alpine
LABEL "org.opencontainers.image.authors"="rodfeitosa28"
# diretorio de trabalho onde o .jar vai ficar e ser executado
WORKDIR /app

# Copia o .jar gerado na etapa de build
COPY --from=builder /app/target/configserver-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta que será escutada dentro do container
EXPOSE 8080

# Comando de execução
# Define o comando fixo de inicialização do container
# Quando o container sobe, ele executa: java -jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]