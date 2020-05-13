node{
  stage('SCM Checkout') {
    git 'https://github.com/neeftb/seproject'
  }
  stage('Compile-Package'){
    def mvnHome = tool name: '', type: 'maven'
    sh "${mvnHome}/bin/mvn package"
  }
}
