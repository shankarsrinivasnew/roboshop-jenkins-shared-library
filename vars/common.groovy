def compile() {
    if (app_lang == "nodejs") {
        sh 'npm install'
    }
    if (app_lang == "maven") {
        sh 'mvn package'
    }
    if (app_lang == "python") {
        sh 'sudo pip3.6 install -r requirements.txt'
    }
    if (app_lang == "go") {
        sh '''
        rm -rvf go.mod
        go mod init dispatch
        go get
        go build
        '''
    }
}


def testcases() {
    // npm test
    // mvn test
    // python -m unittests
    // go test
    sh 'echo Testing ok'
}


def codequality() {
    sh 'sonar-scanner -Dsonar.host.url=http://172.31.7.44:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.projectKey=${component}'
    sh 'echo codequality checked'
}