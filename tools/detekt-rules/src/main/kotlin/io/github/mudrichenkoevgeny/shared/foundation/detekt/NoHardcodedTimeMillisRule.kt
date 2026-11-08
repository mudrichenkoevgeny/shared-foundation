package io.github.mudrichenkoevgeny.shared.foundation.detekt

import dev.detekt.api.Config
import dev.detekt.api.Entity
import dev.detekt.api.Finding
import dev.detekt.api.Rule
import dev.detekt.api.RuleName
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtConstantExpression
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.KtValueArgument
import org.jetbrains.kotlin.psi.psiUtil.getStrictParentOfType

/**
 * Detekt rule prohibiting un-named raw millisecond time constants (e.g. 90000L, 60000L, 86400000L) or time literals.
 */
public class NoHardcodedTimeMillisRule(
    config: Config = Config.empty
) : Rule(
    config = config,
    description =
        "Raw hardcoded millisecond time literals are prohibited. Declare named constants or use Kotlin Duration API."
) {
    override val ruleName: RuleName = RuleName("NoHardcodedTimeMillis")

    private val timeKeywords = listOf(
        "time", "timeout", "duration", "delay", "ttl", "expiry", "interval",
        "period", "millis", "second", "minute", "hour", "day", "wait", "lease"
    )

    override fun visitConstantExpression(expression: KtConstantExpression) {
        super.visitConstantExpression(expression)

        val text = expression.text.removeSuffix("L").removeSuffix("l").replace("_", "")
        val longVal = text.toLongOrNull() ?: return

        val isDivisibleBySecond = longVal >= 1000L && longVal % 1000L == 0L

        val parentProperty = expression.getStrictParentOfType<KtProperty>()
        val parentArg = expression.getStrictParentOfType<KtValueArgument>()

        val propertyName = parentProperty?.name?.lowercase() ?: ""
        val argName = parentArg?.getArgumentName()?.asName?.asString()?.lowercase() ?: ""

        val hasTimeContext = timeKeywords.any { propertyName.contains(it) || argName.contains(it) }

        val isTimeLiteral = isDivisibleBySecond || (longVal >= 100L && hasTimeContext)

        if (isTimeLiteral) {
            if (parentProperty != null && parentProperty.hasModifier(KtTokens.CONST_KEYWORD)) {
                return
            }

            val filePath = expression.containingKtFile.virtualFilePath.replace('\\', '/')
            val isTestOrToolFile = filePath.endsWith("Test.kt") ||
                filePath.contains("/test/") ||
                filePath.contains("/testing/") ||
                filePath.contains("/tools/")

            if (!isTestOrToolFile) {
                report(
                    Finding(
                        entity = Entity.from(expression),
                        message =
                            "Raw hardcoded time literal '${expression.text}' found. " +
                                "Declare a named constant (e.g., const val DEFAULT_TIMEOUT_MILLIS = ...) " +
                                "or use Kotlin Duration API."
                    )
                )
            }
        }
    }
}
