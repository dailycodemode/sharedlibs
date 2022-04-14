package uk.gov.hmcts.contino

class PipelineCallbacksConfig {
  Map<String, Closure> bodies = new HashMap<>()

  void registerBefore(String stage, Closure body) {
    println("PipelineCallbacksConfig-registerBefore")
    bodies.put('before:' + stage, body)
  }

  void registerAfter(String stage, Closure body) {
    println("PipelineCallbacksConfig-registerAfter")
    bodies.put('after:' + stage, body)
  }

  void registerOnStageFailure(Closure body) {
    println("PipelineCallbacksConfig-registerOnStageFailure")
    bodies.put('onStageFailure', body)
  }

  void registerOnFailure(Closure body) {
    println("PipelineCallbacksConfig-registerOnFailure")
    bodies.put('onFailure', body)
  }

  void registerOnSuccess(Closure body) {
    println("PipelineCallbacksConfig-registerOnSuccess")
    bodies.put('onSuccess', body)
  }

  void registerAfterAll(Closure body) {
    println("PipelineCallbacksConfig-registerAfterAll")
    bodies.put('after:all', body)
  }
}
