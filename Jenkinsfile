pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                checkout scm
                sh 'javac src/main/java/com/calculator/Calculator.java'
                sh 'cd src/main/java/ && java com.calculator.Calculator'
            }
        }

        stage('test') {
        	steps {
                sh 'mvn test'
        	}
        }
    }
}