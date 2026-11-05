package io.github.mudrichenkoevgeny.shared.foundation.detekt

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.com.intellij.psi.PsiComment

public class NoInlineCommentRule(
    config: Config = Config.empty
) : Rule(config) {
    override val issue: Issue =
        Issue(
            id = "NoInlineComment",
            severity = Severity.Style,
            description =
                "Banal and redundant inline comments are prohibited. " +
                    "Decompose complex logic into private functions, use KDoc, or use TODO/FIXME/workaround.",
            debt = Debt.FIVE_MINS
        )

    private val allowedPrefixes =
        listOf("/**", "// todo", "// fixme", "// workaround", "// suppress", "// @")

    override fun visitComment(comment: PsiComment) {
        super.visitComment(comment)
        val text = comment.text.trim().lowercase()
        val isAllowed = allowedPrefixes.any { text.startsWith(it) }
        if (!isAllowed) {
            report(
                CodeSmell(
                    issue = issue,
                    entity = Entity.from(comment),
                    message =
                        "Redundant inline comment found: '${comment.text.trim()}'. " +
                            "Decompose steps into named functions or use KDoc for public APIs."
                )
            )
        }
    }
}
