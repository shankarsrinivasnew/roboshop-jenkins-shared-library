def call() {
    // if (!env.sonar_extra_opts) {
    //     env.sonar_extra_opts = ""
    // }
    node ('workstation') {
        try {
            stage (' check  out code') {
                sh 'ls -l'
                cleanWs()
                sh 'ls -l'
                git branch: 'main', url: 'https://github.com/shankarsrinivasnew/cart'
                sh 'ls -l'
            }
            sh 'env'
            stage('compile/build') {
                common.compile()
            }
            stage('testcases') {
                common.testcases()
            }
            stage('codequality') {
                common.codequality()
            }
        }
        catch (e) {
            echo "  you failed , you should try again"
            mail body: "<h1>${component} - pipeline failed \n ${BUILD_URL} </h1>", from: 'shankarsrinivasnew@gmail.com', mimeType: 'text/html', subject: "${component} - pipeline failed ", to: 'shankarsrinivasnew@gmail.com'
        }
    }
}