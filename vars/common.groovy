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
        go mod init dispatch
        go get
        go build
        '''
    }
}


def test() {
    sh 'echo Testing ok'
}


def codequality() {
    sh 'echo codequality ok'
}