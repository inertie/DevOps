#!groovy

pipeline {
    agent any
    tools {
        maven 'maven'
    }
    environment {
        devops = 'test'
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '100'))
        ansiColor('xterm')
    }
    stages {
        stage('desc') {
            steps {
                script {
                    println('Env var: ' + env.devops)
                    sh 'java --version'
                    sh 'mvn --version'
                    sh 'python3 --version'
                    currentBuild.displayName = "#${BUILD_NUMBER} ${params.par1}"
                }
            }
        }
        stage('repo du prof') {
            steps {
                git branch: "${params.branch}",
                        url: 'https://github.com/Ozz007/sb3t.git'
            }
        }
        stage('compilation') {
            steps {
                sh 'mvn compile'
            }
        }
        stage('test unitaire') {
            when{
                expression{ params.test == false }
            }
            steps {
                sh 'mvn test'
            }
        }
        stage('package') {
            steps {
                sh 'mvn package'
            }
        }
        stage('test integration') {
            when{
                expression{ params.test == false }
            }
            steps {
                sh 'mvn verify'
            }
        }
    }
}