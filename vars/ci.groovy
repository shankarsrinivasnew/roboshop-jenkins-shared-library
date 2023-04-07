def call() {
    if (!env.sonar_extra_opts) {
        env.sonar_extra_opts = ""
    }
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
    }
}