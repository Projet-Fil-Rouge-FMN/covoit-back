pipeline {
    agent any
    tools {
        maven 'Maven 3.9.9'
    }
    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/Projet-Fil-Rouge-FMN/covoit-back.git'
            }
        }
    }
}
