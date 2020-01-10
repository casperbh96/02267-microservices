node {
    stage ('Build') {
        checkout scm
        withEnv(["CLASSPATH=/var/lib/jenkins/.m2/repository/dtu/fastmoney/bank/2.0.0/bank-2.0.0.jar:src/main/java/"]) {
            sh 'javac src/main/java/com/dtupay/main.java'
            sh 'java com.dtupay.main'
        }
    }

    stage ('JUnit tests') {
        checkout scm
        sh 'mvn test'
    }

    stage ('Cucumber tests') {
        checkout scm
        // sh 'mvn -D test=CucumberTest test'
        echo "Skipping for now..."
    }
}
