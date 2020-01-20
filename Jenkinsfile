try{
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

    stage ('Customer tests') {
            checkout scm
        sh 'mvn -f CustomerMicroservice/pom.xml test'
        }
    }
} finally {
    node {
        stage ('Docker cleanup') {
            checkout scm
            sh 'docker-compose down'
        }
    }
}

