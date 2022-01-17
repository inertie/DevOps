#!groovy

pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '100'))
        ansiColor('xterm')
    }
    stages {
        stage('destruction') {
            steps {
                sh 'cd /var/jenkins_home/workspace/iac/terraform && terraform destroy -auto-approve'
            }
        }
    }
}
