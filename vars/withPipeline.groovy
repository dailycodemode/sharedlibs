import uk.gov.hmcts.contino.ProjectBranch
import uk.gov.hmcts.contino.SpringBootPipelineType
import uk.gov.hmcts.contino.NodePipelineType
import uk.gov.hmcts.contino.AngularPipelineType

import uk.gov.hmcts.contino.PipelineType

import uk.gov.hmcts.contino.MetricsPublisher
import uk.gov.hmcts.contino.Subscription
import uk.gov.hmcts.pipeline.AKSSubscriptions

import uk.gov.hmcts.contino.AppPipelineConfig
import uk.gov.hmcts.contino.PipelineCallbacksConfig
import uk.gov.hmcts.contino.PipelineCallbacksRunner

import uk.gov.hmcts.contino.AppPipelineDsl
import uk.gov.hmcts.contino.Environment
// import uk.gov.hmcts.pipeline.TeamConfig 


def call(type, String product, String component, Closure body) {

  println("withPipeline-call-START")
  println("env.BRANCH_NAME: " + env.BRANCH_NAME)
  def branch = new ProjectBranch("master") //  env.BRANCH_NAME

  def deploymentNamespace = branch.deploymentNamespace()
  def deploymentProduct = deploymentNamespace ? "$deploymentNamespace-$product" : product

  def pipelineTypes = [
    java  : new SpringBootPipelineType(this, deploymentProduct, component),
    nodejs: new NodePipelineType(this, deploymentProduct, component),
    angular: new AngularPipelineType(this, deploymentProduct, component)
  ]

  Subscription subscription = new Subscription(env)
  AKSSubscriptions aksSubscriptions = new AKSSubscriptions(this)

  PipelineType pipelineType

  if (type instanceof PipelineType) {
    pipelineType = type
  } else {
    pipelineType = pipelineTypes.get(type)
  }

  assert pipelineType != null

  MetricsPublisher metricsPublisher = new MetricsPublisher(this, currentBuild, product, component, subscription.prodName )
  def pipelineConfig = new AppPipelineConfig()
  def callbacks = new PipelineCallbacksConfig()
  def callbacksRunner = new PipelineCallbacksRunner(callbacks)

  callbacks.registerAfterAll { stage ->
    metricsPublisher.publish(stage)
  }

  def dsl = new AppPipelineDsl(this, callbacks, pipelineConfig)
  body.delegate = dsl
  body.call() // register pipeline config

  dsl.onStageFailure() {
    currentBuild.result = "FAILURE"
  }

  Environment environment = new Environment(env)

//   def teamConfig = new TeamConfig(this).setTeamConfigEnv(product)
  String agentType = env.BUILD_AGENT_TYPE
  println("agentType: " + env.BUILD_AGENT_TYPE)

  node(agentType) {
    println("nodeRunning: " + env.BUILD_AGENT_TYPE)
    timeoutWithMsg(time: 180, unit: 'MINUTES', action: 'pipeline') {
    //   def slackChannel = env.BUILD_NOTICES_SLACK_CHANNEL
      try {
        println("RunningWithPipeline")
        dockerAgentSetup()
        println("env.PATH is")
        env.PATH = "$env.PATH:/usr/local/bin"
        println("env.PATH: " + env.PATH)

        sectionBuildAndTest(
          appPipelineConfig: pipelineConfig,
          pipelineCallbacksRunner: callbacksRunner,
          builder: pipelineType.builder,
          subscription: subscription.nonProdName,
          environment: environment.nonProdName,
          product: product,
          component: component,
          pactBrokerUrl: environment.pactBrokerUrl
        )

      } catch (err) {
          println("Error")
    //     if (err.message != null && err.message.startsWith('AUTO_ABORT')) {
    //       currentBuild.result = 'ABORTED'
    //       metricsPublisher.publish(err.message)
    //       return
    //     } else {
    //       currentBuild.result = "FAILURE"
    //       notifyBuildFailure channel: slackChannel
    //       metricsPublisher.publish('Pipeline Failed')
    //     }
    //     callbacksRunner.call('onFailure')
    //     throw err
    //   } finally {
    //     notifyPipelineDeprecations(slackChannel, metricsPublisher)
    //     if (env.KEEP_DIR_FOR_DEBUGGING != "true") {
    //       deleteDir()
    //     }
      }

    //   notifyBuildFixed channel: slackChannel

    //   callbacksRunner.call('onSuccess')
    //   metricsPublisher.publish('Pipeline Succeeded')
    }
  }

  println("withPipeline-call-END")

}
