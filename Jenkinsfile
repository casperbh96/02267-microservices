try {
    node {
        stage ('Build Application') {
            checkout scm
            sh 'mvn -f CustomerMicroservice/pom.xml -D maven.test.skip=true install'
            sh 'mvn -f MerchantMicroservice/pom.xml -D maven.test.skip=true install'
            sh 'mvn -f TokenMicroservice/pom.xml -D maven.test.skip=true install'
        }

        stage ('Build Docker Images') {
            checkout scm
            sh 'docker-compose build'
            sh 'docker-compose up -d'
        }

        stage ('Wait for the server to start up') {
            sh 'sleep 20'
        }

        stage ('Customer tests') {
            checkout scm
            sh 'mvn -f CustomerMicroservice/pom.xml test'
        }

        stage ('Merchant tests') {
            checkout scm
            sh 'mvn -f MerchantMicroservice/pom.xml test'
        }

        stage ('Token tests') {
            checkout scm
            sh 'mvn -f TokenMicroservice/pom.xml test'
        }
    }
} finally {
    node {
        stage ('Docker cleanup') {
            checkout scm
            sh 'docker-compose down'
            sh 'docker image prune -f'
        }
    }
}
