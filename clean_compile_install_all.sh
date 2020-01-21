#!/usr/bin/env bash

cd CustomerMicroservice
mvn clean compile
mvn -D maven.test.skip=true install

cd ../MerchantMicroservice
mvn clean compile
mvn -D maven.test.skip=true install

cd ../TokenMicroservice
mvn clean compile
mvn -D maven.test.skip=true install

cd ../TransactionMicroservice
mvn clean compile
mvn -D maven.test.skip=true install

cd ../DTUPay
mvn clean compile
mvn -D maven.test.skip=true installc