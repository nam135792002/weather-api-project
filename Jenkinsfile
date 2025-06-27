pipeline {
    agent any

    tools {
        jdk 'java2107'
        maven 'maven387'
    }

    stages {
        stage('Initialize Pipeline') {
            steps {
                echo 'Initializing Pipeline...'
                sh 'java -version'
		        sh 'mvn -version'
            }
        }

        stage('Checkout GitHub Code') {
            steps {
                echo 'Checked out GitHub code'
		checkout scmGit(branches: [[name: '*/dev']], extensions: [], userRemoteConfigs: [[credentialsId: 'jenkins-gcp-weather-api-project', url: 'https://github.com/nam135792002/weather-api-project.git']])
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
