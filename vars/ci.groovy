def call () {
    pipeline {
        agent any
        statges {
            stage('compile') {
                sh echo "compile"
            }
            stage('test') {
                sh echo "test"
            }
            stage ('codequality') {
                sh echo "codequality"
            }
        }
    }
}̵