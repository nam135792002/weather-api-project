pipeline {
    agent any

    tools {
        jdk 'java2107'
        maven 'maven387'
    }

    environment {
	SONAR_SCANNER_HOME = tool 'sonar7'
	IMAGE_NAME = "java-app"
	IMAGE_TAG = "${BUILD_NUMBER}"
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
                echo 'Building Weatherforecast Api Project'
		sh 'mvn clean package'
            }
        }

        stage('JUnit Test') {
            steps {
		echo 'JUnit Test'
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo 'Static code analysis with SonarQube'
		withCredentials([string(credentialsId: 'sonartoken', variable: 'sonarToken')]) {
		    withSonarQubeEnv('sonar') {
			    sh '''
					  ${SONAR_SCANNER_HOME}/bin/sonar-scanner \
					  -Dsonar.projectKey=jenkins_gcp_weatherforecast_api_prj \
					  -Dsonar.sources=. \
					  -Dsonar.host.url=http://172.18.0.3:9000 \
       					  -Dsonar.java.binaries=WeatherApiService/target/classes \
					  -Dsonar.token=$sonarToken
       				'''
			}
		}
            }
        }

        stage('Trivy FS Scan') {
            steps {
                echo 'Scanning file system with Trivy FS ...'
		sh 'trivy fs --format table -o FSScanReport.html'
            }
        }

        stage('Build & Tag Docker Image') {
            steps {
                echo 'Building and tagging Docker image'
		script {
			sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
		}
            }
        }

	stage('Trivy Security Scan') {
            steps {
                echo 'Scanning Docker image with Trivy'
		sh '''
  			trivy --severity HIGH,CRITICAL --no-progress --format table -o trivyFSScanReport.html image ${IMAGE_NAME}:${IMAGE_TAG}
     		'''
            }
        }
    }
}
