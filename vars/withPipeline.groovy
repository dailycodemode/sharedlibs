import uk.gov.hmcts.contino.ProjectBranch

def call(type, String product, String component, Closure body) {

  println("withPipeline-call-START")
  println("env.BRANCH_NAME:" + env.BRANCH_NAME)
  println(env.BRANCH_NAME)
  println(BRANCH_NAME)
  println("branch Name")
  def branch = new ProjectBranch(env.BRANCH_NAME)

  def deploymentNamespace = branch.deploymentNamespace()
  def deploymentProduct = deploymentNamespace ? "$deploymentNamespace-$product" : product

  println("withPipeline-call-END")

}
