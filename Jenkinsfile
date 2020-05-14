pipeline {
    agent any
    tools {
        maven 'maven-3'
    }
    stages {
        stage ('Build') {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true install' 
            }
        }
        stage ('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml' 
                }
            }
        }
//        stage ('SonarQube Analysis') {
//            steps {
//                withSonarQubeEnv(installationName: 'sonarqube') {
//                    sh 'mvn clean package sonar:sonar'
//                }
//            }
//        }
    }
}
