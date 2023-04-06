def call() {
    pipeline {
        agent any
        stages {
            stage('compile/build') {
                when {
                    environment name: "app_lang", value: "nodejs"
                }
                steps {
                    echo "compile"
                    sh 'npm install'
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