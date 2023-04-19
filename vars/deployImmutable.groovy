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
                    script {
                        sh 'aws autoscaling start-instance-refresh --auto-scaling-group-name ${component}-${environment}-asg --preferences '{"InstanceWarmup": 180, "MinHealthyPercentage": 90}''
                    }
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