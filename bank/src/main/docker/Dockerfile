FROM openjdk:7
WORKDIR /usr/src
COPY target/webservice-thorntail.jar /usr/src
COPY src/main/python/kml2.py /usr/src/
COPY src/main/python/simplekml-1.3.1 /usr/src/
CMD java -Xmx64m -ea -Drabbitmq_host=rabbitmq -Drabbitmq_exchange=gpstracker -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses=true -Dresteasy.preferJacksonOverJsonB=false -jar webservice-thorntail.jar
