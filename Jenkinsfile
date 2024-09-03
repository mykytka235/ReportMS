def appName = 'be-report'
def branchName = getGitBranchName();
def imageTag = "gcr.io/${env.GOOGLE_CLOUD_PROJECT_NAME}/${appName}:${branchName}.${env.BUILD_NUMBER}"

def getGitBranchName() {
    return scm.branches[0].name.split("/")[1]
}

pipeline {
  agent {
    kubernetes {
      label 'be-report'
      defaultContainer 'jnlp'
      yaml """
apiVersion: v1
kind: Pod
metadata:
labels:
  component: ci
spec:
  # Use service account that can deploy to all namespaces
  serviceAccountName: ${env.K8S_JENKINS_SERVICE_ACCOUNT_NAME}
  containers:
  - name: gcloud
    image: gcr.io/cloud-builders/gcloud
    command:
    - cat
    tty: true
  - name: kubectl
    image: gcr.io/cloud-builders/kubectl
    command:
    - cat
    tty: true
"""
}
  }
  stages {
    stage('Build and push image with Container Builder') {
      steps {
      container('gcloud'){
           sh "gcloud builds submit -t ${imageTag} ."
        }
      }
    }

    stage('Deploy Production') {
      // Production branch

      steps{
        container('kubectl') {
        // Change deployed image in canary to the one we just built
          sh("sed -i.bak 's#cloud-container-registry-container-path-placeholder-for-jenkins#${imageTag}#' ./k8s/production/*.yml")
          sh("kubectl --namespace=default apply -f k8s/services/")
          sh("kubectl --namespace=default apply -f k8s/production/")
        }
      }
    }
  }
}