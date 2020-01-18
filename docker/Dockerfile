FROM adoptopenjdk:8-jre-hotspot

WORKDIR /app

COPY ./target/calculator-artifact-thorntail.jar ./src

WORKDIR /src

CMD java -Xmx64m \
-Djava.net.preferIPv4Stack=true \
-Djava.net.preferIPv4Addresses=true \
-jar calculator-artifact-thorntail.jar

