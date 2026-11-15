package io.github.mudrichenkoevgeny.shared.foundation.tools.projectgenerator

import kotlin.test.Test
import kotlin.test.assertEquals

public class ProjectGeneratorConfigTest {

    @Test
    public fun testDefaultGroupIdAndPackageNameComputation() {
        System.setProperty("projectName", "sample")
        System.setProperty("groupId", "")
        System.setProperty("foundationRootDir", System.getProperty("user.dir"))
        System.setProperty("sharedFoundationVersion", "0.1.17")
        System.setProperty("kotlinVersion", "2.4.21")
        System.setProperty("toolchainJvm", "25")
        System.setProperty("detektVersion", "2.0.0-alpha.6")
        System.setProperty("ktlintVersion", "14.2.0")
        System.setProperty("serializationVersion", "1.12.0")

        val config = ProjectGeneratorConfig.fromSystemProperties()

        assertEquals("com.sample", config.groupId)
        assertEquals("com.sample.shared", config.packageName)
        assertEquals("com/sample/shared", config.packageDirPath)
    }
}
