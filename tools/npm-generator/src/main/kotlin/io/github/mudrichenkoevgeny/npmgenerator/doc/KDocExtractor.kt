package io.github.mudrichenkoevgeny.npmgenerator.doc

import java.io.File
import kotlin.reflect.KClass

/**
 * Utility for extracting and formatting KDoc comments from Kotlin source files into JSDoc.
 */
public class KDocExtractor(sourceDir: File) {

    private val sourceFileMap: Map<String, File> = buildMap {
        if (sourceDir.exists()) {
            sourceDir.walkTopDown().filter { it.isFile && it.extension == "kt" }.forEach { file ->
                val lines = file.readLines()
                val pkgLine = lines.firstOrNull { it.startsWith("package ") }
                val pkgName = pkgLine?.removePrefix("package ")?.trim() ?: ""

                val classRegex = Regex("""(?:class|object|interface|enum\s+class)\s+([A-Za-z0-9_]+)\b""")
                lines.forEach { line ->
                    val match = classRegex.find(line)
                    if (match != null) {
                        val className = match.groupValues[1]
                        val fqn = if (pkgName.isNotEmpty()) "$pkgName.$className" else className
                        put(fqn, file)
                    }
                }
            }
        }
    }

    /**
     * Extracts KDoc documentation for a given class or class member.
     */
    public fun extractKDoc(
        kClass: KClass<*>,
        memberName: String? = null,
        nestedClassName: String? = null
    ): String? {
        val className = kClass.simpleName ?: return null
        val fqn = kClass.java.name
        val file = sourceFileMap[fqn] ?: return null
        val fullText = file.readText()

        val text = if (nestedClassName != null) {
            val nestedBlockRegex =
                Regex("""(?:object|class)\s+$nestedClassName\b[\s\S]*?(?=(?:object|class)\s+\w+\b|$)""")
            nestedBlockRegex.find(fullText)?.value ?: fullText
        } else {
            fullText
        }

        val targetName = memberName ?: nestedClassName ?: className
        val directKDoc = extractKDocForDecl(text, targetName)
        if (directKDoc != null) return directKDoc

        if (memberName != null) {
            return extractPropertyKDocFromClass(fullText, className, memberName)
        }

        return null
    }

    private fun extractKDocForDecl(text: String, targetName: String): String? {
        val declRegex = Regex(
            """(?:internal\s+|public\s+|private\s+|const\s+|override\s+|val\s+|var\s+|fun\s+|object\s+|class\s+|interface\s+|enum\s+class\s+)+$targetName\b"""
        )
        val declMatch = declRegex.find(text) ?: return null
        val declStart = declMatch.range.first
        val prefix = text.substring(0, declStart)
        val lastDocStart = prefix.lastIndexOf("/**")
        if (lastDocStart == -1) return null

        val lastDocEnd = prefix.indexOf("*/", lastDocStart)
        if (lastDocEnd == -1 || lastDocEnd <= lastDocStart) return null

        val between = prefix.substring(lastDocEnd + 2)
        val hasOtherDecl = between.contains("const ") ||
            between.contains("val ") ||
            between.contains("var ") ||
            between.contains("object ") ||
            between.contains("class ")
        if (hasOtherDecl) return null

        val rawDoc = prefix.substring(lastDocStart + 3, lastDocEnd)
        val cleaned = rawDoc.lines()
            .map { it.trimStart().removePrefix("*").trim() }
            .filter { !it.startsWith("@param") && !it.startsWith("@return") }
            .map { it.cleanKDocLinks() }
            .filter { it.isNotBlank() }
            .joinToString("\n * ")

        return if (cleaned.isNotBlank()) "/**\n * $cleaned\n */" else null
    }

    private fun extractPropertyKDocFromClass(fullText: String, className: String, memberName: String): String? {
        val classKDocMatch = Regex(
            """/\*\*([\s\S]*?)\*/\s*(?:@[^\r\n]+\s*)*(?:internal\s+|public\s+|private\s+|data\s+|value\s+|sealed\s+)*(?:class|object|interface|enum\s+class)\s+$className\b"""
        ).find(fullText) ?: return null

        val classKDocBody = classKDocMatch.groupValues[1]
        val propRegex = Regex("""@property\s+$memberName\b\s+([^\r\n*]+)""")
        val propMatch = propRegex.find(classKDocBody) ?: return null

        val desc = propMatch.groupValues[1].trim().cleanKDocLinks()
        return "/**\n * $desc\n */"
    }
}

/**
 * Cleans KDoc symbol links symbol into simple names.
 */
public fun String.cleanKDocLinks(): String {
    return this.replace(Regex("""\[([^\]]+)]""")) { match ->
        val group = match.groupValues[1]
        group.substringAfterLast('.')
    }
}

/**
 * Formats a JSDoc string with proper indentation.
 */
public fun String.formatAsJsDoc(indent: String = ""): String {
    val lines = this.lines()
    if (indent.isEmpty()) return lines.joinToString("\n")
    return lines.joinToString("\n") { line ->
        if (line.isBlank()) line else "$indent$line"
    }
}
