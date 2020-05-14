pipeline {
    agent any
    tools {
        maven 'maven-3'
        sonar 'sonar-scanner'
    }
    stages {
        stage('SCM Checkout') {
            steps {
                git 'https://github.com/neeftb/seproject'
            }
        }
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
        stage ('SonarQube Analysis') {
            steps {
                withSonarQubeEnv() {
                    sh 'mvn clean package sonar:sonar'
                }
            }
        }
    }
}
