def call() {
    pipeline {
        agent any
        stages {
            stage('compile/build') {
                steps {
                    script {
                        if (app_lang == "nodejs") {
                            sh 'npm install'
                        }
                        if (app_lang == "maven") {
                            sh 'mvn package'
                        }
                    }
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

