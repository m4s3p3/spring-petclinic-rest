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

        SONAR_HOST = 'http://host.docker.internal:9000'

        // Clave del proyecto backend
        PROJECT_KEY = 'sonar-project-backend'
    }

    stages {
        stage('Build, Test & Analyze') {
            steps {
                script {
                    // Usamos 'sh' porque Jenkins corre en Linux (Docker)
                    sh "mvn clean verify jacoco:report sonar:sonar -Dsonar.projectKey=${PROJECT_KEY} -Dsonar.host.url=${SONAR_HOST} -Dsonar.token=${SONAR_TOKEN}"
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
