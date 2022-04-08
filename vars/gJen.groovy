def call(Closure body) {
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
