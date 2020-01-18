# 02267-microservices

## For running in IntelliJ

1. Go To File -> Project Structure and choose SDK and Project language as 8.
2. Open the Maven sidebar in the top right. Click the M icon for Maven and execute the following
    - `mvn clean compile`
    - `mvn install`
3. When done, press the left-most button, that looks like a sync button.
    
## For running a service in Terminal

1. `java -jar target/calculator-artifact-thorntail.jar`

## For running docker from Terminal

1. `docker-compose build`
2. ``