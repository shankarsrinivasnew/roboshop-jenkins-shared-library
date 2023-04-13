def compile() {
    if (!env.sonar_extra_opts) {
        env.sonar_extra_opts = ""
    }
    // cart , catalogue , user
    if (app_lang == "nodejs") {
        sh 'npm install'
    }
    // shipping
    if (app_lang == "maven") {
        sh 'mvn package'
    }
    // payment
    if (app_lang == "python") {
        sh 'sudo pip3.6 install -r requirements.txt'
    }
    // dispatch
    if (app_lang == "go") {
        sh '''
        rm -rvf go.mod
        go mod init dispatch
        go get
        go build
        '''
    }
    // frontend is angular and do need compilation
}


def testcases() {
    // npm test
    // mvn test
    // python -m unittests
    // go test
    sh 'echo Testing ok'
}

def codequality() {
    withAWSParameterStore(credentialsId: 'AWSCRED', naming: 'absolute', path: '/sonarcube', recursive: true, regionName: 'us-east-1') {
        // sh 'sonar-scanner -Dsonar.host.url=http://172.31.7.44:9000 -Dsonar.login=${SONARCUBE_USER} -Dsonar.password=${SONARCUBE_PASS} -Dsonar.projectKey=${component} ${sonar_extra_opts} -Dsonar.qualitygate.wait=true'
        sh 'echo codequality checked'
    }
}

def prepareArtifacts() {
    if (app_lang == "maven") {
        
    }
    else zip
}

def uploadArtifacts() {
    sh 'echo uploading artifacts'
}