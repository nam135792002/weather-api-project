pipeline {
    agent any

    stages {
        stage('Initialize Pipeline') {
            steps {
                echo 'Initializing Pipeline...'
            }
        }

        stage('Checkout GitHub Code') {
            steps {
                git branch: 'dev', url: 'https://github.com/nam135792002/weather-api-project.git'
                echo 'Checked out GitHub code'
            }
        }

        stage('Maven Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('JUnit Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo 'Static code analysis with SonarQube (placeholder)'
            }
        }

        stage('Trivy Scan') {
            steps {
                echo 'Scanning Docker image with Trivy (placeholder)'
            }
        }

        stage('Build & Tag Docker Image') {
            steps {
                echo 'Building and tagging Docker image (placeholder)'
            }
        }
    }
}
