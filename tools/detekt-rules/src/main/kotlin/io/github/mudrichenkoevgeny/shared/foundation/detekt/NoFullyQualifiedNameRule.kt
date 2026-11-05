package io.github.mudrichenkoevgeny.shared.foundation.detekt

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression
import org.jetbrains.kotlin.psi.KtImportDirective
import org.jetbrains.kotlin.psi.KtPackageDirective
import org.jetbrains.kotlin.psi.psiUtil.getStrictParentOfType

public class NoFullyQualifiedNameRule(
    config: Config = Config.empty
) : Rule(config) {
    override val issue: Issue =
        Issue(
            id = "NoFullyQualifiedName",
            severity = Severity.Style,
            description = "Fully Qualified Names (FQN) in code bodies are prohibited. Import the symbol explicitly.",
            debt = Debt.FIVE_MINS
        )

    private val fqnRegex = Regex("""^(?:kotlin|kotlinx|java|javax|android|androidx|com|org|io|net|dev)\.[a-z0-9_.]+\.[A-Z]\w+""")

    override fun visitDotQualifiedExpression(expression: KtDotQualifiedExpression) {
        super.visitDotQualifiedExpression(expression)
        val isImportOrPackage =
            expression.getStrictParentOfType<KtImportDirective>() != null ||
                expression.getStrictParentOfType<KtPackageDirective>() != null

        if (isImportOrPackage) return

        // Process only top-level dot qualified expressions to avoid duplicate reports for nested sub-expressions
        val isNestedInDotExpression = expression.getStrictParentOfType<KtDotQualifiedExpression>() != null
        if (isNestedInDotExpression) return

        val text = expression.text
        if (fqnRegex.containsMatchIn(text)) {
            report(
                CodeSmell(
                    issue = issue,
                    entity = Entity.from(expression),
                    message = "Fully Qualified Name '$text' found in code. Import the symbol explicitly instead of using FQN."
                )
            )
        }
    }
}
