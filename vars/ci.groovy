def call () {
    pipeline {
        agent any
        statges {
            stage('compile') {
                echo "compile"
            }
            stage('test') {
                echo "test"
            }
            stage ('codequality') {
                echo "codequality"
            }
        }
    }
}̵