package io.github.mudrichenkoevgeny.shared.foundation.generator.script

import java.io.File
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PostInstallScriptGeneratorTest {

    @Test
    fun testFilteringOfKotlinAndSharedSubdirectoriesInDomainRules() {
        val tempRootDir = File("build/tmp/test-postinstall-rules-root")
        val tempOutputDir = File("build/tmp/test-postinstall-rules-output")

        if (tempRootDir.exists()) tempRootDir.deleteRecursively()
        if (tempOutputDir.exists()) tempOutputDir.deleteRecursively()

        try {
            val domainRuleDir = File(tempRootDir, ".agent/global/hivepick")
            File(domainRuleDir, "common").mkdirs()
            File(domainRuleDir, "common/rule.md").writeText("Common rule")

            File(domainRuleDir, "web").mkdirs()
            File(domainRuleDir, "web/web-rule.md").writeText("Web rule")

            File(domainRuleDir, "kotlin").mkdirs()
            File(domainRuleDir, "kotlin/kotlin-rule.md").writeText("Kotlin rule")

            File(domainRuleDir, "shared").mkdirs()
            File(domainRuleDir, "shared/shared-rule.md").writeText("Shared rule")

            PostInstallScriptGenerator.generateScriptAndSyncAgentRules(
                rootDir = tempRootDir,
                outputDir = tempOutputDir,
                packageName = "@hivepick/shared"
            )

            val outputHivepickDir = File(tempOutputDir, ".agent/hivepick")

            assertTrue(File(outputHivepickDir, "common/rule.md").exists(), "common/rule.md should exist")
            assertTrue(File(outputHivepickDir, "web/web-rule.md").exists(), "web/web-rule.md should exist")

            assertFalse(File(outputHivepickDir, "kotlin").exists(), "kotlin subdirectory should be filtered out")
            assertFalse(File(outputHivepickDir, "shared").exists(), "shared subdirectory should be filtered out")
        } finally {
            tempRootDir.deleteRecursively()
            tempOutputDir.deleteRecursively()
        }
    }
}
