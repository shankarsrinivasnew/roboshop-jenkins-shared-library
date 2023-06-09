def call() {
    // if (!env.sonar_extra_opts) {
    //     env.sonar_extra_opts = ""
    // }
    node ('workstation') {
        try {
            stage (' check  out code') {
                cleanWs()
                git branch: 'main', url: "https://github.com/shankarsrinivasnew/${component}"
            }
            sh 'env'
            if (env.BRANCH_NAME != "main") {
                stage('compile/build') {
                    common.compile()
                }
            }
            if (!env.TAG_NAME && env.BRANCH_NAME != "main") {
                stage('testcases') {
                    common.testcases()
                }
            }
            if (env.TAG_NAME) {
                stage('prepareArtifacts') {
                    common.prepareArtifacts()
                }
                stage('uploadArtifacts') {
                    common.uploadArtifacts()
                }
            }
            if (env.BRANCH_NAME ==~ "PR-.*") {
                stage('codequality') {
                    common.codequality()
                }
            }
        }
        catch (e) {
            echo "  you failed , you should try again"
            mail body: "<h1>${component} - pipeline failed \n ${BUILD_URL} </h1>", from: 'shankarsrinivasnew@gmail.com', mimeType: 'text/html', subject: "${component} - pipeline failed ", to: 'shankarsrinivasnew@gmail.com'
        }
    }
}