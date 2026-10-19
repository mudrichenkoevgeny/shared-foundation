package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.email

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.email.EmailTemplate

fun createTestEmailTemplate(
    subject: String = "Test Subject",
    body: String = "<p>Test Body</p>"
) = EmailTemplate(
    subject = subject,
    body = body
)
