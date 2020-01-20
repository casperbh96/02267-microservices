node {
    stage ('Build Customer') {
        checkout scm
        sh 'mvn -f CustomerMicroservice/pom.xml clean compile'
        sh 'mvn -f CustomerMicroservice/pom.xml -D maven.test.skip=true install'
    }

    stage ('Build Docker Images') {
        checkout scm
        sh 'docker-compose build'
        sh 'docker-compose up -d'
    }

    stage ('JUnit tests') {
        checkout scm
        sh 'mvn -D maven.test.skip=true install'
        sh 'mvn -D test=com.dtupay.junit.** test'
    }

    stage ('Cucumber tests') {
        checkout scm
        sh 'mvn -D maven.test.skip=true install'
        sh 'mvn -D test=com.dtupay.cucumber.** test'
    }
    
    stage ('Docker cleanup') {
        checkout scm
        sh 'docker-compose down'
    }
}
