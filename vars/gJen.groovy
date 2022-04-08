def call(Closure body) {
  body.call()
  
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
