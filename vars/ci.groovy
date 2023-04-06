def call() {
    pipeline {
        agent any
        stages {
            stage('compile') {
                steps {
                    echo "compile"
                }
            }
            stage('test') {
                steps {
                    echo "test"
                }
            }
            stage('codequality') {
                steps {
                    echo "codequality"
                }
            }
        }
    }
}