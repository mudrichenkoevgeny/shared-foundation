package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.security.password

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.password.EmailPasswordChangeRequest

fun createTestEmailPasswordChangeRequest(
    oldPassword: String = "OldSecret123!",
    newPassword: String = "NewSecret123!"
) = EmailPasswordChangeRequest(
    oldPassword = oldPassword,
    newPassword = newPassword
)
