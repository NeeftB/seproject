pipeline {
    agent any
    tools {
        maven 'maven-3'
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
    }
}
