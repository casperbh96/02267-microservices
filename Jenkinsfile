node {
    stage ('Build') {
        checkout scm
        withEnv(["CLASSPATH=/var/lib/jenkins/.m2/repository/dtu/fastmoney/bank/2.0.0/bank-2.0.0.jar:src/main/java/"]) {
            sh 'javac src/main/java/com/dtupay/app/Main.java'
            sh 'java com.dtupay.app.Main'
        }
    }

    stage ('JUnit tests') {
        checkout scm
        sh 'mvn -D test=com.dtupay.junit.** test'
    }

    stage ('Cucumber tests') {
        checkout scm
        sh 'mvn -D test=com.dtupay.cucumber.** test'
    }
}
