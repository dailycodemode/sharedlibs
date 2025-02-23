package uk.gov.hmcts.contino.azure

class Az {

  def steps
  def subscription
  def az

  Az(steps, subscription) {
    println("Az-cstr")
    this.steps = steps
    this.subscription = subscription

    this.az = { cmd ->
      return steps.sh(label: "az ${cmd}", script: "env AZURE_CONFIG_DIR=/opt/jenkins/.azure-${this.subscription} az ${cmd}", returnStdout: true)?.trim()
    }
  }

}
