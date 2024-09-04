pipeline {
    agent any
    tools {
        jdk 'java'
        maven 'Maven 3.9.9'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/ssdfred/covoit-back'
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
                // Running JaCoCo for code coverage.
                sh 'mvn jacoco:report'
            }
            post {
                always {
                    // Archiving the code coverage report.
                    jacoco execPattern: '**/target/jacoco.exec'
                }
            }
        }
        stage('SonarQube Analysis') {
           steps {
				script {
				def mvnHome = tool 'Maven 3.9.9' 
				withSonarQubeEnv('SonarQC') {
					sh "${mvnHome}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=covoit -Dsonar.projectName='covoit'"
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
