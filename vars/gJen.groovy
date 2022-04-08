def call() {
  pipeline {
    agent any

      stages {
          stage('PipelineBuilder') {
              steps {
                  echo 'PipelineBuilder..'
              }
          }
      }
  }
}
