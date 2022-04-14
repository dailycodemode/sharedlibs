package uk.gov.hmcts.contino

class AppPipelineDsl extends CommonPipelineDsl implements Serializable {
  final AppPipelineConfig config
  def final steps

  AppPipelineDsl(Object steps, PipelineCallbacksConfig callbacks, AppPipelineConfig config) {
    super(steps, callbacks, config)
    println("AppPipelineDsl-cstr")
    this.config = config
    this.steps = steps
  }

  void loadVaultSecrets(Map<String, List<Map<String, Object>>> vaultSecrets) {
    println("AppPipelineDsl-loadVaultSecrets")
    config.vaultSecrets = vaultSecrets
  }

  void enableDbMigration(String dbMigrationVaultName) {
    println("AppPipelineDsl-enableDbMigration")
    config.migrateDb = true
    config.dbMigrationVaultName = dbMigrationVaultName
  }

  void enablePerformanceTest(int timeout = 15) {
    println("AppPipelineDsl-enablePerformanceTest")
    config.perfTestTimeout = timeout
    config.performanceTest = true
  }

  void enableApiGatewayTest(int timeout = 15) {
    println("AppPipelineDsl-enableApiGatewayTest")
    config.apiGatewayTestTimeout = timeout
    config.apiGatewayTest = true
  }

  void enableCrossBrowserTest(int timeout = 120) {
    println("AppPipelineDsl-enableCrossBrowserTest")
    config.crossBrowserTestTimeout = timeout
    config.crossBrowserTest = true
  }

  void enableCrossBrowserTest(List<String> browsers, int timeout = 120) {
    println("AppPipelineDsl-enableCrossBrowserTest")
    config.crossBrowserTestTimeout = timeout
    config.parallelCrossBrowsers = browsers
  }

  void enableSecurityScan(int timeout = 120) {
    println("AppPipelineDsl-enableSecurityScan")
    config.securityScanTimeout = timeout
    config.securityScan = true
  }

  void enableFullFunctionalTest(int timeout = 30) {
    println("AppPipelineDsl-enableFullFunctionalTest")
    config.fullFunctionalTestTimeout = timeout
    config.fullFunctionalTest = true
  }

  void enableMutationTest(int timeout = 120) {
    println("AppPipelineDsl-enableMutationTest")
    config.mutationTestTimeout = timeout
    config.mutationTest = true
  }

  void disableLegacyDeployment() {
    println("AppPipelineDsl-disableLegacyDeployment")
    config.legacyDeployment = false
  }

  void disableLegacyDeploymentOnAAT() {
    println("AppPipelineDsl-disableLegacyDeploymentOnAAT")
    config.legacyDeploymentExemptions.add("aat")
  }

  void nonServiceApp() {
    println("AppPipelineDsl-nonServiceApp")
    config.serviceApp = false
  }

  void overrideVaultEnvironments(Map<String, String> vaultOverrides) {
    println("AppPipelineDsl-overrideVaultEnvironments")
    config.vaultEnvironmentOverrides = vaultOverrides
  }

  void enableAksStagingDeployment() {
    println("AppPipelineDsl-enableAksStagingDeployment")
    config.aksStagingDeployment = true
  }

  void enableCleanupOfHelmReleaseOnSuccess() {
    println("AppPipelineDsl-enableCleanupOfHelmReleaseOnSuccess")
    config.clearHelmRelease = true;
  }

  enum PactRoles { CONSUMER, PROVIDER, CONSUMER_DEPLOY_CHECK}

  void enablePactAs(List<PactRoles> roles) {
    println("AppPipelineDsl-enablePactAs")
    config.pactBrokerEnabled = true
    config.pactConsumerTestsEnabled = roles.contains(PactRoles.CONSUMER)
    config.pactProviderVerificationsEnabled = roles.contains(PactRoles.PROVIDER)
    config.pactConsumerCanIDeployEnabled = roles.contains(PactRoles.CONSUMER_DEPLOY_CHECK)
  }

  void enableHighLevelDataSetup() {
    println("AppPipelineDsl-enableHighLevelDataSetup")
    config.highLevelDataSetup = true
  }

  void enableFortifyScan(String fortifyVaultName = "") {
    println("AppPipelineDsl-enableFortifyScan")
    config.fortifyScan = true
    config.fortifyVaultName = fortifyVaultName
  }
}
