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
                            sh 'pip3.6 install -r requirements.txt'
                        }
                        if (app_lang == "python") {
                            sh 'mvn package'
                        }
                        if (app_lang == "go") {
                            sh 'go mod init dispatch'
                            sh ' go get'
                            sh 'go build'
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