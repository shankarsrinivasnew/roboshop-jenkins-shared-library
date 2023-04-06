def call() {
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
            stage('test') {
                steps {
                    script {
                        common.test()
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