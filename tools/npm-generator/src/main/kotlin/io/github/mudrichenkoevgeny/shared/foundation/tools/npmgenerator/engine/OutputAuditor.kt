package io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.engine

import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.util.decapitalizeFirstLetter
import kotlin.reflect.KClass

/**
 * Cleans JavaScript content by stripping comments and string literal interiors.
 */
public fun cleanJsContent(content: String): String {
    val sb = StringBuilder(content.length)
    val state = JsLexerState(content)

    while (state.index < state.length) {
        when {
            state.inSingleComment -> processSingleCommentState(state, sb)
            state.inMultiComment -> processMultiCommentState(state, sb)
            state.inStringChar != null -> processStringLiteralState(state, sb)
            else -> processNormalCodeState(state, sb)
        }
    }

    return sb.toString()
}

private class JsLexerState(val content: String) {
    var index = 0
    var inSingleComment = false
    var inMultiComment = false
    var inStringChar: Char? = null
    val length = content.length

    fun current(): Char = content[index]
    fun next(): Char = if (index + 1 < length) content[index + 1] else ' '
}

private fun processSingleCommentState(state: JsLexerState, sb: StringBuilder) {
    val c = state.current()
    if (c == '\n' || c == '\r') {
        state.inSingleComment = false
        sb.append(c)
    }
    state.index++
}

private fun processMultiCommentState(state: JsLexerState, sb: StringBuilder) {
    val c = state.current()
    if (c == '*' && state.next() == '/') {
        state.inMultiComment = false
        state.index += 2
    } else {
        if (c == '\n') sb.append('\n')
        state.index++
    }
}

private fun processStringLiteralState(state: JsLexerState, sb: StringBuilder) {
    val c = state.current()
    if (c == '\\') {
        state.index += 2
        return
    }
    if (c == state.inStringChar) {
        state.inStringChar = null
        sb.append('"')
    }
    state.index++
}

private fun processNormalCodeState(state: JsLexerState, sb: StringBuilder) {
    val c = state.current()
    val next = state.next()

    if (c == '/' && next == '/') {
        state.inSingleComment = true
        state.index += 2
        return
    }

    if (c == '/' && next == '*') {
        state.inMultiComment = true
        state.index += 2
        return
    }

    if (c == '"' || c == '\'' || c == '`') {
        state.inStringChar = c
        sb.append('"')
        state.index++
        return
    }

    sb.append(c)
    state.index++
}

internal fun auditGeneratedOutput(
    config: NpmGeneratorConfig,
    fullJsContent: String,
    dtsContent: String,
    importedFoundationJs: Set<String>
) {
    val cleanJs = cleanJsContent(fullJsContent)
    val cleanDts = cleanJsContent(dtsContent)

    verifyBraceMatching(cleanJs, "index.js")
    verifyBraceMatching(cleanDts, "index.d.ts")

    check(!dtsContent.contains("Partial<any>")) { "Generated index.d.ts must not contain Partial<any>" }
    check(!dtsContent.contains(": any;") && !dtsContent.contains(": any,")) { "Generated index.d.ts must not contain ': any;'" }

    auditExportUniqueness(fullJsContent, "index.js")
    auditExportUniqueness(dtsContent, "index.d.ts")

    verifyRegisteredExportsPresence(config, fullJsContent, dtsContent)
    verifyDomainMappersPresence(config, fullJsContent, dtsContent)
    verifyDownstreamAutoImports(config, fullJsContent, importedFoundationJs)
}

private fun verifyRegisteredExportsPresence(
    config: NpmGeneratorConfig,
    fullJsContent: String,
    dtsContent: String
) {
    verifyClassExports(config.dtoClasses, fullJsContent, dtsContent) { it.decapitalizeFirstLetter() + "Schema" }
    verifyClassExports(config.enumClasses, fullJsContent, dtsContent) { it }
    verifyClassExports(config.brandedTypes, fullJsContent, dtsContent) { "to${it}OrThrow" }
    verifyClassExports(config.routeObjects, fullJsContent, dtsContent) { it }
    verifyClassExports(config.errorClasses, fullJsContent, dtsContent) { it }
    verifyClassExports(config.permClasses, fullJsContent, dtsContent) { it }
    verifyClassExports(config.contractClasses, fullJsContent, dtsContent) { it }
}

private fun verifyClassExports(
    classes: List<*>,
    fullJsContent: String,
    dtsContent: String,
    symbolExtractor: (String) -> String
) {
    classes.forEach { item ->
        val kClass = item as? KClass<*> ?: return@forEach
        val name = kClass.simpleName ?: return@forEach
        val symbol = symbolExtractor(name)
        val isPresent = fullJsContent.contains(symbol) || dtsContent.contains(symbol)
        check(isPresent) { "Expected export symbol '$symbol' for class '$name' was not found in generated output" }
    }
}

private fun verifyDomainMappersPresence(
    config: NpmGeneratorConfig,
    fullJsContent: String,
    dtsContent: String
) {
    config.domainToDtoMappers.forEach { (domainClass, payloadClass) ->
        val domainName = domainClass.simpleName ?: return@forEach
        check(dtsContent.contains("export interface $domainName")) {
            "Domain interface 'export interface $domainName' was not found in index.d.ts"
        }
        check(fullJsContent.contains("to$domainName")) {
            "Domain mapper 'to$domainName' was not found in index.js"
        }
        if (payloadClass != null) {
            val payloadName = payloadClass.simpleName ?: return@forEach
            check(fullJsContent.contains("to$payloadName")) {
                "Payload mapper 'to$payloadName' was not found in index.js"
            }
        }
    }
}

private fun verifyDownstreamAutoImports(
    config: NpmGeneratorConfig,
    fullJsContent: String,
    importedFoundationJs: Set<String>
) {
    val isFoundation = config.basePackagePrefix.isEmpty() ||
        (config.foundationImportPackage.isNotEmpty() && config.packageName == config.foundationImportPackage)

    if (isFoundation) return

    val bodyLines = fullJsContent.lines().filter { !it.trimStart().startsWith("import ") }
    val cleanJsBody = cleanJsContent(bodyLines.joinToString("\n"))

    val baseUtils = setOf(
        "toQueryMap",
        "toQuerySearchString",
        "createTestList",
        "formatLocalizedString",
        "defaultLocalizedStringResolver",
        "pagedResultSchema"
    )
    val helperSymbols = config.brandedTypesNeedingHelpers.map { "to${it}OrThrow" }.toSet()
    val symbolsToVerify = config.autoImportSymbols + baseUtils + helperSymbols

    symbolsToVerify.forEach { symbol ->
        val regex = Regex("""\b${Regex.escape(symbol)}\b""")
        if (regex.containsMatchIn(cleanJsBody)) {
            check(symbol in importedFoundationJs) {
                "Downstream package used symbol '$symbol' in code, but failed to track it in importedFoundationJs"
            }
        }
    }
}

private fun verifyBraceMatching(cleanContent: String, fileName: String) {
    var depth = 0
    for (i in cleanContent.indices) {
        if (cleanContent[i] == '{') {
            depth++
        } else if (cleanContent[i] == '}') {
            depth--
        }
        check(depth >= 0) { "Unexpected closing brace '}' in $fileName at character $i" }
    }
    check(depth == 0) { "Mismatched braces in $fileName (depth delta: $depth)" }
}

private fun auditExportUniqueness(content: String, fileName: String) {
    val typeRegex = Regex("""export\s+(?:type|interface)\s+([A-Za-z0-9_$]+)""")
    val valueRegex = Regex("""export\s+(?:const|function|class|declare\s+const)\s+([A-Za-z0-9_$]+)""")

    val typeCounts = mutableMapOf<String, Int>()
    val valueCounts = mutableMapOf<String, Int>()

    content.lines().forEach { line ->
        typeRegex.find(line)?.groupValues?.get(1)?.let { name ->
            typeCounts[name] = (typeCounts[name] ?: 0) + 1
        }
        valueRegex.find(line)?.groupValues?.get(1)?.let { name ->
            valueCounts[name] = (valueCounts[name] ?: 0) + 1
        }
    }

    val duplicateTypes = typeCounts.filter { it.value > 1 }
    val duplicateValues = valueCounts.filter { it.value > 1 }

    check(duplicateTypes.isEmpty()) {
        "Duplicate type export declarations detected in $fileName: ${duplicateTypes.keys}"
    }
    check(duplicateValues.isEmpty()) {
        "Duplicate value export declarations detected in $fileName: ${duplicateValues.keys}"
    }
}
