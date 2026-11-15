package io.github.mudrichenkoevgeny.shared.foundation.tools.projectgenerator

import java.io.File

internal fun main() {
    ProjectGeneratorVerifier.verify()
}

internal object ProjectGeneratorVerifier {

    private const val TEST_PROJECT_NAME = "scaffold-test"
    private const val TEST_SKELETON_REL_PATH = "build/tmp/scaffold-test/scaffold-test-shared"

    private const val PROP_PROJECT_NAME = "projectName"
    private const val PROP_TARGET_DIR = "targetDir"
    private const val PROP_LINK_LOCAL = "linkLocal"
    private const val PROP_SKIP_GIT = "skipGit"
    private const val PROP_FOUNDATION_ROOT_DIR = "foundationRootDir"

    private const val PROP_OS_NAME = "os.name"
    private const val PROP_JAVA_HOME = "java.home"
    private const val ENV_JAVA_HOME = "JAVA_HOME"

    private const val GRADLEW_BAT = "gradlew.bat"
    private const val GRADLEW_SH = "./gradlew"

    private const val TASK_CHECK = "check"
    private const val TASK_PUBLISH_TO_MAVEN_LOCAL = "publishToMavenLocal"
    private const val TASK_GENERATE_NPM_PACKAGE = "generateNpmPackage"
    private const val FLAG_NO_DAEMON = "--no-daemon"
    private const val FLAG_STACKTRACE = "--stacktrace"

    private const val OS_WIN = "win"
    private const val VALUE_TRUE = "true"

    fun verify() {
        val foundationRootDir = File(
            System.getProperty(PROP_FOUNDATION_ROOT_DIR)
                ?: error("System property '$PROP_FOUNDATION_ROOT_DIR' is missing.")
        )

        val testSkeletonDir = File(foundationRootDir, TEST_SKELETON_REL_PATH)
        if (testSkeletonDir.exists()) {
            testSkeletonDir.deleteRecursively()
        }

        System.setProperty(PROP_PROJECT_NAME, TEST_PROJECT_NAME)
        System.setProperty(PROP_TARGET_DIR, testSkeletonDir.absolutePath)
        System.setProperty(PROP_LINK_LOCAL, VALUE_TRUE)
        System.setProperty(PROP_SKIP_GIT, VALUE_TRUE)

        val config = ProjectGeneratorConfig.fromSystemProperties()

        println("Scaffolding test repository directly in-process...")
        ProjectGenerator(config).generate()

        println("Verifying generated scaffold project in ${testSkeletonDir.absolutePath}...")
        val isWindows = System.getProperty(PROP_OS_NAME).lowercase().contains(OS_WIN)
        val gradlewCmd = if (isWindows) GRADLEW_BAT else GRADLEW_SH
        val javaHome = System.getProperty(PROP_JAVA_HOME)

        val buildPb = ProcessBuilder(
            testSkeletonDir.resolve(gradlewCmd).absolutePath,
            TASK_CHECK,
            TASK_PUBLISH_TO_MAVEN_LOCAL,
            TASK_GENERATE_NPM_PACKAGE,
            FLAG_NO_DAEMON,
            FLAG_STACKTRACE
        ).directory(testSkeletonDir).redirectErrorStream(true)

        if (!buildPb.environment().containsKey(ENV_JAVA_HOME) && !javaHome.isNullOrBlank()) {
            buildPb.environment()[ENV_JAVA_HOME] = javaHome
        }

        val buildProcess = buildPb.start()
        val buildOutput = buildProcess.inputStream.bufferedReader().use { it.readText() }
        val buildExit = buildProcess.waitFor()
        if (buildExit != 0) {
            println("Scaffolded project build output:\n$buildOutput")
            error("Verification build failed in scaffolded project with exit code $buildExit")
        }

        println("Project Generator E2E verification passed successfully!")
    }
}
