package io.github.mudrichenkoevgeny.shared.foundation.tools.projectgenerator

import kotlin.test.Test
import kotlin.test.assertEquals

class ProjectGeneratorConfigTest {

    @Test
    fun testDefaultGroupIdAndPackageNameComputation() {
        try {
            System.setProperty("projectName", "sample")
            System.setProperty("groupId", "")
            System.setProperty("foundationRootDir", System.getProperty("user.dir"))
            System.setProperty("sharedFoundationVersion", "1.0.0")
            System.setProperty("kotlinVersion", "1.0.0")
            System.setProperty("toolchainJvm", "21")
            System.setProperty("detektVersion", "1.0.0")
            System.setProperty("ktlintVersion", "1.0.0")
            System.setProperty("serializationVersion", "1.0.0")

            val config = ProjectGeneratorConfig.fromSystemProperties()

            assertEquals("com.sample", config.groupId)
            assertEquals("com.sample.shared", config.packageName)
            assertEquals("com/sample/shared", config.packageDirPath)
        } finally {
            System.clearProperty("projectName")
            System.clearProperty("groupId")
            System.clearProperty("foundationRootDir")
            System.clearProperty("sharedFoundationVersion")
            System.clearProperty("kotlinVersion")
            System.clearProperty("toolchainJvm")
            System.clearProperty("detektVersion")
            System.clearProperty("ktlintVersion")
            System.clearProperty("serializationVersion")
        }
    }
}
