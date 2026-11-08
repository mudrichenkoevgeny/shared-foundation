package io.github.mudrichenkoevgeny.shared.foundation.detekt

import dev.detekt.api.Config
import dev.detekt.api.Entity
import dev.detekt.api.Finding
import dev.detekt.api.Rule
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression

/**
 * Detekt rule enforcing the use of the `.value` property over `.name` or `.serialName`
 * when referencing domain enums or wire types.
 */
public class PreferValueOverNameRule(
    config: Config = Config.empty
) : Rule(
        config = config,
        description = "Use '.value' property instead of '.name' or '.serialName' when " +
            "referencing domain enums or wire types."
    ) {
    private val enumEntryPattern = Regex("""^[A-Z][a-zA-Z0-9_]*\.[A-Z0-9_]+$""")
    private val enumPropertyPattern =
        Regex(
            pattern =
                "role|status|category|visibility|type|actorType|order|mode|provider|" +
                    "sensitivity|action|resource|method|target|format|level",
            options = setOf(RegexOption.IGNORE_CASE)
        )

    override fun visitDotQualifiedExpression(expression: KtDotQualifiedExpression) {
        super.visitDotQualifiedExpression(expression)
        val selectorText = expression.selectorExpression?.text ?: return
        val receiverText = expression.receiverExpression.text

        if (selectorText == "serialName") {
            report(
                Finding(
                    entity = Entity.from(expression),
                    message =
                        "Do not use '.serialName' on domain enums or wire types. " +
                            "Use the '.value' property instead as per project standards."
                )
            )
            return
        }

        if (selectorText == "name") {
            val isEnumCall =
                enumEntryPattern.matches(receiverText) ||
                    enumPropertyPattern.containsMatchIn(receiverText)
            if (isEnumCall) {
                report(
                    Finding(
                        entity = Entity.from(expression),
                        message =
                            "Do not use '.name' on domain enums. " +
                                "Use the '.value' property for wire string representations as per project standards."
                    )
                )
            }
        }
    }
}
