#!/usr/bin/env bash

mvn -f CustomerMicroservice/pom.xml -D maven.test.skip=true install
mvn -f MerchantMicroservice/pom.xml -D maven.test.skip=true install
mvn -f TokenMicroservice/pom.xml -D maven.test.skip=true install
mvn -f TransactionMicroservice/pom.xml -D maven.test.skip=true install
mvn -f DTUPay/pom.xml -D maven.test.skip=true install
