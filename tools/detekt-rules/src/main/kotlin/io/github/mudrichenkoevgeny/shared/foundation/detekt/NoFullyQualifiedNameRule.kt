package io.github.mudrichenkoevgeny.shared.foundation.detekt

import dev.detekt.api.Config
import dev.detekt.api.Entity
import dev.detekt.api.Finding
import dev.detekt.api.Rule
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression
import org.jetbrains.kotlin.psi.KtImportDirective
import org.jetbrains.kotlin.psi.KtPackageDirective
import org.jetbrains.kotlin.psi.psiUtil.getStrictParentOfType

/**
 * Detekt rule prohibiting Fully Qualified Names (FQN) inside code bodies.
 */
public class NoFullyQualifiedNameRule(
    config: Config = Config.empty
) : Rule(
        config = config,
        description = "Fully Qualified Names (FQN) in code bodies are prohibited. Import the symbol explicitly."
    ) {
    private val fqnRegex =
        Regex("""^(?:kotlin|kotlinx|java|javax|android|androidx|com|org|io|net|dev)\.[a-z0-9_.]+\.[A-Z]\w+""")

    override fun visitDotQualifiedExpression(expression: KtDotQualifiedExpression) {
        super.visitDotQualifiedExpression(expression)
        val isImportOrPackage =
            expression.getStrictParentOfType<KtImportDirective>() != null ||
                expression.getStrictParentOfType<KtPackageDirective>() != null

        if (isImportOrPackage) return

        val isNestedInDotExpression = expression.getStrictParentOfType<KtDotQualifiedExpression>() != null
        if (isNestedInDotExpression) return

        val text = expression.text
        if (fqnRegex.containsMatchIn(text)) {
            report(
                Finding(
                    entity = Entity.from(expression),
                    message =
                        "Fully Qualified Name '$text' found in code. " +
                            "Import the symbol explicitly instead of using FQN."
                )
            )
        }
    }
}
