def call() {
    pipeline {
        agent any
        options {
            ansiColor('xterm')
        }
        parameters {
            string(name: 'environment', defaultValue: 'prod', description: 'Environment')
            string(name: 'component', defaultValue: '', description: 'component name ')
            string(name: 'app_version', defaultValue: '', description: 'Application tag version')
        }
        stages {
            stage (' check  out code') {
                steps {
                    dir('APP') {
                        git branch: 'main', url: "https://github.com/shankarsrinivasnew/${component}"
                    }
                }
            }
            stage('deploy servers') {
                steps {
                    script {
                        sh 'helm upgrade -i ${component} . -f APP/helm/prod.yaml --set appversion=${app_version}'
                    }
                }
            }
        }
        post {
            always {
                cleanWs()
            }
        }
    }
}