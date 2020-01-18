FROM adoptopenjdk:8-jre-hotspot

WORKDIR /app

COPY ./target/calculator-artifact-thorntail.jar ./src/monolith.jar

CMD [ "java", "-jar", "./src/monolith.jar" ]

