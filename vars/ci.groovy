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
                mail body: "<h1>${component} - pipeline failed \n ${BUILD_URL} </h1>", from: 'shankarsrinivasnew@gmail.com', mimeType: 'text/html', subject: "${component} - pipeline failed ", to: 'shankarsrinivasnew@gmail.com'
            }
        }
    }
}