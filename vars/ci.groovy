def call() {
    // if (!env.sonar_extra_opts) {
    //     env.sonar_extra_opts = ""
    // }
    pipeline {
        agent any
        stages {
            stage('compile/build') {
                steps {
                    script {
                        common.compile()
                        env
                    }
                }
            }
            stage('testcases') {
                steps {
                    script {
                        common.testcases()
                    }
                }
            }
            stage('codequality') {
                steps {
                    script {
                        common.codequality()
                    }
                }
            }
        }
        post {
            failure {
                echo "  you failed , you should try again"
                mail bcc: '', body: ' jenkins stage failure', cc: '', from: 'shankarsrinivasnew@gmail.com', mimeType: 'text/html', replyTo: '', subject: ' jenkins stage failure', to: 'shankarsrinivasnew@gmail.com'
            }
        }
    }
}