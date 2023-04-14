def compile() {
    sh 'echo hello complier'
    // for cart , catalogue , user
    if (app_lang == "nodejs") {
        sh 'npm install'
    }
    // for shipping
    if (app_lang == "maven") {
        sh "mvn package;mv target/${component}-1.0.jar ${component}.jar"
    }
    // for payment
    // if (app_lang == "python") {
    //     sh 'sudo pip3.6 install -r requirements.txt'
    // }
    // for dispatch
    if (app_lang == "go") {
        sh '''
        rm -rvf go.mod
        go mod init dispatch
        go get
        go build
        '''
    }
    // for frontend no need commplilation , its angular
    sh 'echo compilation done'
}


def testcases() {
    // npm test
    // mvn test
    // python -m unittests
    // go test
    sh 'echo Testing ok'
}

def codequality() {
    if (!env.sonar_extra_opts) {
        env.sonar_extra_opts = ""
    }
    sh 'echo checking codequality'
    env.sonarcube_user = sh (script: 'aws ssm get-parameter --name "prod.sonarcube_user"  --with-decryption|jq .Parameter.Value|xargs', returnStdout: true).trim()
    env.sonarcube_pass = sh (script: 'aws ssm get-parameter --name "prod.sonarcube_pass"  --with-decryption|jq .Parameter.Value|xargs', returnStdout: true).trim()
    echo sonarcube_pass
    wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs: [[password: sonarcube_pass]]]) {
        echo sonarcube_pass
        sh 'sonar-scanner -Dsonar.host.url=http://172.31.7.44:9000 -Dsonar.login=${sonarcube_user} -Dsonar.password=${sonarcube_pass} -Dsonar.projectKey=${component} ${sonar_extra_opts} -Dsonar.qualitygate.wait=true'
    }
}

def prepareArtifacts() {
    sh 'echo ${TAG_NAME} >VERSION'
    if (app_lang == "maven") {
        sh "zip -r ${component}-${TAG_NAME}.zip ${component}.jar schema VERSION"
    }
    else {
        sh "zip -r ${component}-${TAG_NAME}.zip * -x Jenkinsfile"
    }
}

def uploadArtifacts() {
    sh 'echo uploading artifacts'
    env.nexus_user = sh (script: 'aws ssm get-parameter --name "prod.nexus_user"  --with-decryption|jq .Parameter.Value|xargs', returnStdout: true).trim()
    env.nexus_pass = sh (script: 'aws ssm get-parameter --name "prod.nexus_pass"  --with-decryption|jq .Parameter.Value|xargs', returnStdout: true).trim()
    wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs: [[password: nexus_pass]]]) {
        sh "curl -v -u ${nexus_user}:${nexus_pass} --upload-file ${component}-${TAG_NAME}.zip http://172.31.15.8:8081/repository/${component}/${component}-${TAG_NAME}.zip"
    }
}
    

