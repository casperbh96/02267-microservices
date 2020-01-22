# 02267-microservices

## For running in IntelliJ

1. Right click on the POM.xml file in each Microservice and DTUPay, and click +Add as Maven project.
2. Go To File -> Project Structure and choose SDK and Project language as 8.
3. Open the Maven sidebar in the top right, and do the following commands for each Microservice and DTUPay
    - `mvn clean compile`
    - `mvn install`
4. When done, press the left-most button, that looks like a sync button.

## For running in Terminal

1. In the root of the project, run the bash file `sh mvn_install_all.sh`.
2. Run `docker-compose build`
3. Run `docker-compose up`
4. When step 3 is done, you can run tests or access the service through the exposed APIs. See Javadocs for implementation.
