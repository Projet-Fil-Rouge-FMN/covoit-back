pipeline {
    agent any
    tools {
        jdk 'java'
        maven 'Maven_3.9.9'
    }
    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/Projet-Fil-Rouge-FMN/covoit-back.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        stage('Code Coverage') {
            steps {
                jacoco execPattern: '**/target/*.exec'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQubeServer') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
    }
    post {
        success {
            echo 'Build Successful'
        }
        failure {
            echo 'Build Failed'
        }
    }
}