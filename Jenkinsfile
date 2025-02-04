pipeline {
  agent any

  stages {
    stage('Build') {
      steps {
        // Checkout source code
        checkout scm
        sh 'echo "starting build ./mvnw"'
        sh './mvnw'

        // Build the application
        sh './mvnw clean package'
      }
    }

    stage('Unit Test') {
      steps {
        // Run unit tests
        sh './mvnw.cmd test'
      }
    }

    stage('Integration Test') {
      steps {
        // Start Docker container
        sh 'docker-compose up -d'

        // Run integration tests
        sh './mvnw integration-test'

        // Stop Docker container
        sh 'docker-compose down'
      }
    }

    stage('Build Docker Image') {
      steps {
        // Build Docker image
        sh 'docker build -t fetch-receipt-processor .'
      }
    }

    stage('Deploy') {
      steps {
        // Run Docker container
        sh 'docker run -p 8081:8080 --name fetch-receipt-processor-container fetch-receipt-processor'
      }
    }
  }
}