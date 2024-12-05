# Trabajo Final de Grado: Exploración de la Metodología BDD con Cucumber

Este proyecto es una API HTTP desarrollada con Java Spring Boot, enfocada en la exploración de la metodología Behavior Driven Development (BDD) utilizando Cucumber. La API utiliza autenticación JWT.

## Requisitos

- Java 17
- Maven 3.9.6
- Docker (para ejecutar los tests de aceptación con Testcontainers)
- Una instancia de MariaDB/MySQL corriendo en `localhost:3306`

## Instalación

1. Configurar la base de datos:

- Es necesario tener una instancia de MariaDB/MySQL corriendo en localhost:3306. Para para correr una instancia MariaDB rápidamente se puede usar este comando de Docker:

`docker run --name mariadb -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mariadb`

2. Construir la aplicación:

Click derecho en el editor y clicar a "refresh maven" o ejecutar por comando:

`mvn clean install`

## Ejecución de Tests

Para ejecutar los tests de aceptación, es necesario tener Docker instalado, ya que se utiliza Testcontainers para iniciar una imagen de MySQL como base de datos temporal. Usar el siguiente comando para ejecutar los tests:

`mvn test`

Tambien se puede usar IntellIJ para ejecutarlos mediante un click derecho a un archivo con extension .feature en \src\test\resources\features "run feature".

## Recomendaciones

Se recomienda usar IntelliJ IDEA como IDE para desarrollar y ejecutar este proyecto, ya que ofrece una excelente integración con las herramientas utilizadas (Spring Boot, Maven, Docker, etc.).
