def call() {
    pipeline {
        agent any
        parameters {
            string(name: 'ENV', defaultValue: 'prod', description: 'Which Environment?')
            string(name: 'ACTION', defaultValue: 'init', description: 'Which Action?')
        }
        stages {
            stage('init') {
                steps {
                    sh 'terraform ${ACTION} -backend-config=/env-${ENV}/state.tfvars'
                }
            }
            stage("plan") {
                steps {
                    sh 'terraform ${ACTION} -var-file=/env-${ENV}/${ENV}.tfvars'
                }
            }
            stage("apply") {
                steps {
                    sh 'terraform ${ACTION} -auto-approve -var-file=/env-${ENV}/${ENV}.tfvars'
                }
            }
        }
    }
}
