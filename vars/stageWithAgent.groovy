/**
 * Stage with Agent selection
 *
 */
def call(String name, String product, Closure body) {
  println("stageWithAgent-cstr")
  stage(name) {
    withDockerAgent(product, body)
  }
}
