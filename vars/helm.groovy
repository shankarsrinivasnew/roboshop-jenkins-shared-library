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
                    dir('HELM') {
                        git branch: 'main', url: "https://github.com/shankarsrinivasnew/roboshop-helm-chart"
                    }
                }
            }
            stage('deploy servers') {
                steps {
                    script {
                        sh 'helm upgrade -i ${component} HELM/ -f APP/helm/prod.yaml --set appversion=${app_version}'
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