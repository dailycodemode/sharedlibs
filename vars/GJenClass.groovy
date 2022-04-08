class GJenClass {

  String branchName

  GJenClass(String branchName) {
    this.branchName = branchName
  }

  boolean isMaster() {
    branchName == 'master'
  }
}
