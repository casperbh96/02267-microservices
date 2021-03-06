// author Dumitru
try {
    def CONTAINER_NAMES = ['token', 'customer', 'transaction', 'merchant', 'dtupay']

    node {
        stage ('Build Application') {
            checkout scm
            sh './mvn_install_all.sh'
        }

        stage ('Build Docker Images') {
            checkout scm
            sh 'docker-compose build'
            sh 'docker-compose up -d'
        }

        stage ('Wait for the server to start up') {
            def container_output
            CONTAINER_NAMES.each {
                container_output = ""
                while (!container_output.contains("Thorntail is Ready")) {
                    sh "sleep 2"
                    container_output = sh (script: "docker logs ${it}", returnStdout: true)
                }
            }
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

        stage ('Transaction tests') {
            checkout scm
            sh 'mvn -f TransactionMicroservice/pom.xml test'
        }
        
        stage ('DTUPay tests') {
            checkout scm
            sh 'mvn -f DTUPay/pom.xml test'
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
