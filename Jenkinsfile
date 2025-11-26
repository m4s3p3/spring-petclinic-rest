pipeline {
    agent any

    tools {
        // Asegúrate de que en Jenkins (Administrar Jenkins > Tools) tienes configurado
        // Maven con el nombre 'Maven 3' (o ajusta este nombre)
        maven 'Maven 3'
        jdk 'Java 17'
    }

    environment {
        // Tu token que sabemos que funciona (copiado de tu PDF)
        SONAR_TOKEN = 'squ_cafeb7d15918025d1fe900115605437d090e9701'

        // IMPORTANTE:
        // Si Jenkins está instalado en Windows y Sonar en Docker: usa 'http://localhost:9000'
        // Si Jenkins TAMBIÉN está en Docker: usa 'http://host.docker.internal:9000'
        SONAR_HOST = 'http://localhost:9000'

        // Clave del proyecto backend
        PROJECT_KEY = 'sonar-project-backend'
    }

    stages {
        stage('Checkout') {
            steps {
                // Descarga el código de tu repositorio (Asegúrate de que esta URL es la de tu fork)
                // Cambia 'main' por 'master' si tu rama principal se llama así
                git branch: 'main', url: 'https://github.com/m4s3p3/spring-petclinic-rest.git'
            }
        }

        stage('Build, Test & Analyze') {
            steps {
                script {
                    // Este es el comando "mágico" que te funcionó, adaptado a Jenkins
                    // Usamos 'bat' porque estás en Windows
                    bat "mvn clean verify jacoco:report sonar:sonar -Dsonar.projectKey=${PROJECT_KEY} -Dsonar.host.url=${SONAR_HOST} -Dsonar.token=${SONAR_TOKEN}"
                }
            }
        }
    }

    post {
        always {
            // Recoge los resultados de los tests (JUnit) para mostrarlos en Jenkins
            junit 'target/surefire-reports/*.xml'
        }
    }
}
