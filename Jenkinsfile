try {
    node {
        stage ('Build Application') {
            checkout scm
            sh 'mvn -f CustomerMicroservice/pom.xml -D maven.test.skip=true install'
            sh 'mvn -f TokenMicroservice/pom.xml -D maven.test.skip=true install'
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
        }
    }
}
