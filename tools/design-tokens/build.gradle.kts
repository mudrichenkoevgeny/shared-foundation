import java.io.File

plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.kotlin.test)
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

private class IndexSpec(
    val subDir: String,
    val extension: String,
    val indexFileName: String
)

val generateResourceIndexes = tasks.register("generateResourceIndexes") {
    group = "build"
    description = "Generates plain-text manifest indexes for design tokens and font resources."

    val resourcesDir = file("src/main/resources/design-tokens")
    inputs.dir(resourcesDir)

    val outputDir = layout.buildDirectory.dir("generated/resources/designTokens")
    outputs.dir(outputDir)

    doLast {
        val baseOut = File(outputDir.get().asFile, "design-tokens")
        baseOut.mkdirs()

        val indexSpecs = listOf(
            IndexSpec("tokens", ".json", "tokens-index.txt"),
            IndexSpec("fonts/ttf", ".ttf", "fonts-ttf-index.txt"),
            IndexSpec("fonts/woff2", ".woff2", "fonts-woff2-index.txt")
        )

        indexSpecs.forEach { spec ->
            val targetDir = File(resourcesDir, spec.subDir)
            val files = targetDir.listFiles()?.toList().orEmpty()
                .map { it.name }
                .filter { it.endsWith(spec.extension) }
                .sorted()
            File(baseOut, spec.indexFileName).writeText(files.joinToString("\n"))
        }
    }
}

sourceSets["main"].resources.srcDir(generateResourceIndexes)

tasks.register<JavaExec>("generatePenpotTokens") {
    group = "documentation"
    description = "Generates Penpot JSON and TypeScript design tokens artifacts."
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("io.github.mudrichenkoevgeny.shared.foundation.tools.designtokens.GeneratePenpotTokensTaskKt")
    systemProperty("outputDir", layout.buildDirectory.dir("penpot").get().asFile.absolutePath)
    dependsOn(generateResourceIndexes)
}
