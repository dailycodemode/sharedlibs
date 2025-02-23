package uk.gov.hmcts.contino

class ProjectBranch {

  String branchName

  ProjectBranch(String branchName) {
      println("withPipeline-cstr")
    this.branchName = Objects.requireNonNull(branchName)
  }

  boolean isMaster() {
    branchName == 'master'
  }

  boolean isPreview() {
    branchName == 'preview'
  }

  boolean isPR() {
    branchName.startsWith('PR')
  }

  boolean isDemo() {
    branchName == 'demo'
  }

  boolean isPerftest() {
    branchName == 'perftest'
  }

  boolean isIthc() {
    branchName == 'ithc'
  }

  String deploymentNamespace() {
    // lowercase because some Azure resource names require lowercase
    return (isPR()) ? branchName.toLowerCase() : ""
  }

  String imageTag() {
    if (isPR()) {
      return branchName.toLowerCase()
    }
    return (branchName == 'master') ? 'staging' : this.branchName.replaceAll('/', '-')
  }

}
