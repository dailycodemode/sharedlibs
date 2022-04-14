package uk.gov.hmcts.contino

abstract class CommonPipelineDsl implements Serializable {
  final PipelineCallbacksConfig callbacks
  final CommonPipelineConfig config
  def final steps

  CommonPipelineDsl(Object steps, PipelineCallbacksConfig callbacks, CommonPipelineConfig config) {
    println("CommonPipelineDsl-cstr")
    this.callbacks = callbacks
    this.config = config
    this.steps = steps
  }

  void afterCheckout(Closure body) {
    println("CommonPipelineDsl-afterCheckout")
    after('checkout', body)
  }

  void before(String stage, Closure body) {
    println("CommonPipelineDsl-before")
    callbacks.registerBefore(stage, body)
  }

  void after(String stage, Closure body) {
    println("CommonPipelineDsl-after")
    callbacks.registerAfter(stage, body)
  }

  void onStageFailure(Closure body) {
    println("CommonPipelineDsl-onStageFailure")
    callbacks.registerOnStageFailure(body)
  }

  void onFailure(Closure body) {
    println("CommonPipelineDsl-onFailure")
    callbacks.registerOnFailure(body)
  }

  void onSuccess(Closure body) {
    println("CommonPipelineDsl-onSuccess")
    callbacks.registerOnSuccess(body)
  }

  void enableSlackNotifications(String slackChannel) {
    println("CommonPipelineDsl-enableSlackNotifications")
    config.slackChannel = slackChannel
    steps.env.BUILD_NOTICE_SLACK_CHANNEL = slackChannel
  }

  void syncBranchesWithMaster(List<String> branches) {
    println("CommonPipelineDsl-syncBranchesWithMaster")
    config.branchesToSyncWithMaster = branches
  }
}