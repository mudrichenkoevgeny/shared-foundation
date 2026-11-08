package io.github.mudrichenkoevgeny.shared.foundation.detekt

import dev.detekt.api.Config
import dev.detekt.api.Entity
import dev.detekt.api.Finding
import dev.detekt.api.Rule
import dev.detekt.api.RuleName
import org.jetbrains.kotlin.psi.KtWhenExpression

/**
 * Detekt rule advising against 'else' branch in 'when' expressions over sealed types or enums.
 */
public class AvoidElseInSealedWhenRule(
    config: Config = Config.empty
) : Rule(
    config = config,
    description =
        "Avoid 'else' branch in 'when' expressions over sealed types or enums to allow compiler exhaustive checks."
) {
    override val ruleName: RuleName = RuleName("AvoidElseInSealedWhen")

    override fun visitWhenExpression(expression: KtWhenExpression) {
        super.visitWhenExpression(expression)

        val elseEntry = expression.entries.firstOrNull { it.isElse } ?: return
        val subject = expression.subjectExpression ?: return

        val nonElseEntries = expression.entries.filter { !it.isElse }
        val allEntriesAreEnumOrSealedVariants = nonElseEntries.isNotEmpty() && nonElseEntries.all { entry ->
            entry.conditions.all { condition ->
                val text = condition.text.trim().removePrefix("is ").trim()
                text.firstOrNull()?.isUpperCase() == true &&
                    !text.startsWith("String") &&
                    !text.startsWith("Number") &&
                    !text.startsWith("Boolean") &&
                    !text.startsWith("Int") &&
                    !text.startsWith("Long") &&
                    !text.startsWith("Double") &&
                    !text.startsWith("Float") &&
                    !text.startsWith("Any") &&
                    !text.startsWith("List") &&
                    !text.startsWith("Map") &&
                    !text.startsWith("Set") &&
                    !text.startsWith("Iterable") &&
                    !text.startsWith("Collection")
            }
        }

        if (!allEntriesAreEnumOrSealedVariants) return

        val filePath = expression.containingKtFile.virtualFilePath.replace('\\', '/')
        val isTestOrToolFile = filePath.endsWith("Test.kt") ||
            filePath.contains("/test/") ||
            filePath.contains("/testing/") ||
            filePath.contains("/tools/")

        if (!isTestOrToolFile) {
            report(
                Finding(
                    entity = Entity.from(elseEntry),
                    message =
                        "Avoid using 'else' branch in 'when' expression over '${subject.text}'. " +
                            "List all variants explicitly so adding new cases triggers compile-time errors."
                )
            )
        }
    }
}
