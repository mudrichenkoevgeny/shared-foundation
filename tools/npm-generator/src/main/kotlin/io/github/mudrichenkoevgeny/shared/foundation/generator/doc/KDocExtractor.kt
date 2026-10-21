package io.github.mudrichenkoevgeny.shared.foundation.generator.doc

import java.io.File
import kotlin.reflect.KClass

val sourceFileMap: Map<String, File> by lazy {
    val currentDir = File(System.getProperty("user.dir"))
    val rootDir = if (currentDir.name == "npm-generator") currentDir.parentFile.parentFile else currentDir
    val sourceDir = File(rootDir, "shared/foundation")
    val map = mutableMapOf<String, File>()
    if (sourceDir.exists()) {
        sourceDir.walkTopDown().filter { it.isFile && it.extension == "kt" }.forEach { file ->
            map[file.nameWithoutExtension] = file
        }
    }
    map
}

fun extractKDoc(kClass: KClass<*>, memberName: String? = null, nestedClassName: String? = null): String? {
    val className = kClass.simpleName ?: return null
    val file = sourceFileMap[className] ?: return null
    val fullText = file.readText()

    val text = if (nestedClassName != null) {
        val nestedBlockRegex = Regex("""(?:object|class)\s+$nestedClassName\b[\s\S]*?(?=(?:(?:object|class)\s+\w+\b)|$)""")
        nestedBlockRegex.find(fullText)?.value ?: fullText
    } else {
        fullText
    }

    val targetName = memberName ?: nestedClassName ?: className

    val declRegex = Regex("""(?:internal\s+|public\s+|private\s+|const\s+|override\s+|val\s+|var\s+|fun\s+|object\s+|class\s+|interface\s+|enum\s+class\s+)+$targetName\b""")
    val declMatch = declRegex.find(text)
    if (declMatch != null) {
        val declStart = declMatch.range.first
        val prefix = text.substring(0, declStart)
        val lastDocStart = prefix.lastIndexOf("/**")
        if (lastDocStart != -1) {
            val lastDocEnd = prefix.indexOf("*/", lastDocStart)
            if (lastDocEnd != -1 && lastDocEnd > lastDocStart) {
                val between = prefix.substring(lastDocEnd + 2)
                val hasOtherDecl = between.contains("const ") || between.contains("val ") || between.contains("var ") || between.contains("object ") || between.contains("class ")
                if (!hasOtherDecl) {
                    val rawDoc = prefix.substring(lastDocStart + 3, lastDocEnd)
                    val cleaned = rawDoc.lines()
                        .map { it.trimStart().removePrefix("*").trim() }
                        .filter { !it.startsWith("@param") && !it.startsWith("@return") }
                        .joinToString("\n * ") { it.cleanKDocLinks() }
                        .trim()

                    if (cleaned.isNotBlank()) {
                        return "/**\n * $cleaned\n */"
                    }
                }
            }
        }
    }

    if (memberName != null) {
        val classKDocMatch = Regex("""/\*\*([\s\S]*?)\*/\s*(?:@[^\r\n]+\s*)*(?:class|object|interface|enum\s+class)\s+$className\b""").find(fullText)
        if (classKDocMatch != null) {
            val classKDocBody = classKDocMatch.groupValues[1]
            val propRegex = Regex("""@property\s+$memberName\b\s+([^\r\n*]+)""")
            val propMatch = propRegex.find(classKDocBody)
            if (propMatch != null) {
                val desc = propMatch.groupValues[1].trim().cleanKDocLinks()
                return "/**\n * $desc\n */"
            }
        }
    }

    return null
}

fun String.cleanKDocLinks(): String {
    return this.replace(Regex("""\[([^\]]+)\]""")) { match ->
        val inner = match.groupValues[1]
        inner.substringAfterLast('.')
    }
}

fun String.formatAsJsDoc(indent: String = ""): String {
    val lines = this.lines()
    if (indent.isEmpty()) return lines.joinToString("\n")
    return lines.joinToString("\n") { line ->
        if (line.isBlank()) line else "$indent${line.trimStart()}"
    }
}
