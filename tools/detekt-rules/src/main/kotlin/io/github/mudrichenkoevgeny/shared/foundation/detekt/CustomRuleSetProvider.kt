package io.github.mudrichenkoevgeny.shared.foundation.detekt

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

public class CustomRuleSetProvider : RuleSetProvider {
    override val ruleSetId: String = "custom-rules"

    override fun instance(config: Config): RuleSet =
        RuleSet(
            ruleSetId,
            listOf(
                NoInlineCommentRule(config),
                NoFullyQualifiedNameRule(config)
            )
        )
}
