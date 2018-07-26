pipeline {
    agent none
    stages {
        stage('Run Tests') {
            parallel {
                stage('Build Maven Container') {
                    agent {
                        kubernetes {
                            label 'declarative-pod'
                            containerTemplate {
                                name 'maven'
                                image 'maven:3.3.9-jdk-8-alpine'
                                ttyEnabled true
                                command 'cat'
                            }
                        }
                    }
                    steps {
                        sh 'set'
                        sh 'test -f /usr/bin/mvn' // checking backwards compatibility
                        sh "echo OUTSIDE_CONTAINER_ENV_VAR = ${CONTAINER_ENV_VAR}"
                        container('maven') {
                            sh 'echo INSIDE_CONTAINER_ENV_VAR = ${CONTAINER_ENV_VAR}'
                            sh 'mvn -version'
                            sleep 120
                        }
                    }
                }
                stage('Build K8s Container') {
                    agent {
                        kubernetes {
                            label 'declarative-node-pod'
                            containerTemplate {
                                name 'node'
                                image 'node:latest'
                                ttyEnabled true
                                command 'cat'
                            }
                        }
                    }
                    steps {
                        container('node') {
                            sh 'echo INSIDE_CONTAINER_ENV_VAR = ${CONTAINER_ENV_VAR}'
                            sh 'node --version'
                            sleep 120
                        }
                    }
                }
            }
        }
    }
}
