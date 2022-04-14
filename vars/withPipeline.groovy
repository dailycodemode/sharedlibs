import uk.gov.hmcts.contino.ProjectBranch

def call(type, String product, String component, Closure body) {

  println("withPipeline-call-START")
  def branch = new ProjectBranch("master") // env.BRANCH_NAME

  def deploymentNamespace = branch.deploymentNamespace()
  def deploymentProduct = deploymentNamespace ? "$deploymentNamespace-$product" : product

  println("withPipeline-call-END")

}
