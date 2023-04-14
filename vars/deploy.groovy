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
            stage('update parameter store') {
                steps {
                    sh 'aws ssm put-parameter --name "${environment}.${component}_app_version" --type "String" --value "${app_version}" --overwrite'
                }
            }
            stage('deploy servers') {
                steps {
                    sh 'aws ec2 describe-instances --filters "Name=tag:Name,Values=${component}_${environment}" --query "Reservations[*].Instances[*].PrivateIpAddress" --output text >/tmp/servers'
                    sh 'ansible-playbook -i /tmp/servers -e role_name=${component} -e env=${environment} -e ansible_user=centos -e ansible_password=DevOps321'
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