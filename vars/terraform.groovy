def call() {
    pipeline {
        agent any
        parameters {
            string(name: 'ENV', defaultValue: 'prod', description: 'Which Environment?')
            choice(name: 'ACTION', choices: ['init', 'plan', 'apply'], description: 'Pick something')
        }
        stages {
            stage('init') {
                steps {
                    sh 'terraform init -backend-config=env-${ENV}/state.tfvars'
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
                when {
                    environment name: 'ACTION', value: 'apply'
                }
                steps {
                    sh 'terraform ${ACTION} -auto-approve -var-file=env-${ENV}/${ENV}.tfvars'
                    echo "apply done"
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
