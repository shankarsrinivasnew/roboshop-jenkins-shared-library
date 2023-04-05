def call() {
    pipeline {
        agent any
        parameters {
            string(name: 'ENV', defaultValue: 'prod', description: 'Which Environment?')
            string(name: 'ACTION', defaultValue: 'init', description: 'Which Action?')
        }
        stages {
            stage('init') {
                when {
                    environment name: 'ACTION', value: 'init'
                }
                steps {
                    sh 'terraform ${ACTION} -backend-config=env-${ENV}/state.tfvars'
                }
            }
            stage("plan") {
                when {
                    environment name: 'ACTION', value: 'plan'
                }
                steps {
                    sh 'terraform ${ACTION} -var-file=env-${ENV}/${ENV}.tfvars'
                }
            }
            stage("apply") {
                steps {
                    when {
                        environment name: 'ACTION', value: 'apply'
                    }
                    // sh 'terraform ${ACTION} -auto-approve -var-file=env-${ENV}/${ENV}.tfvars'
                    echo "apply done"
                }
            }
        }
    }
}
