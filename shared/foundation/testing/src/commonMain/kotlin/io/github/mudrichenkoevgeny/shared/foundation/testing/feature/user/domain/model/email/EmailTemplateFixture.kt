package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.email

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.email.EmailTemplate

/**
 * Creates a test [EmailTemplate] domain instance.
 *
 * @param subject Email subject string template.
 * @param body Email HTML body content string template.
 */
fun createTestEmailTemplate(
    subject: String = "Test Subject",
    body: String = "<p>Test Body</p>"
) = EmailTemplate(
    subject = subject,
    body = body
)
