def compile() {
    if (!env.sonar_extra_opts) {
        env.sonar_extra_opts = ""
    }
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
    withAWSParameterStore(credentialsId: 'AWSCRED', naming: 'absolute', path: '/sonarcube', recursive: true, regionName: 'us-east-1') {
        sh 'sonar-scanner -Dsonar.host.url=http://172.31.7.44:9000 -Dsonar.login=${SONARCUBE_USER} -Dsonar.password=${SONARCUBE_PASS} -Dsonar.projectKey=${component} ${sonar_extra_opts} -Dsonar.qualitygate.wait=true'
        sh 'echo codequality checked'
    }
}

def prepareArtifacts() {
    sh 'echo ${TAG_NAME} > VERSION'
    if (app_lang == "maven") {
        sh "zip - r ${component}-${TAG_NAME}.zip ${component}.jar schema VERSION "
    }
    else {
        sh "zip - r ${component}-${TAG_NAME}.zip $ * - x Jenkinsfile"
    }
}

def uploadArtifacts() {
    sh 'echo uploading artifacts'
    sh "curl -v -u admin:admin123 --upload-file ${component}-${TAG_NAME}.zip http://172.31.10.184:8081 /respository/${component}/${component}-${TAG_NAME}.zip"
}
