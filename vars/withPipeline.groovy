import uk.gov.hmcts.contino.ProjectBranch

def call(type, String product, String component, Closure body) {

  def branch = new ProjectBranch(env.BRANCH_NAME)

  def deploymentNamespace = branch.deploymentNamespace()
  def deploymentProduct = deploymentNamespace ? "$deploymentNamespace-$product" : product

}
