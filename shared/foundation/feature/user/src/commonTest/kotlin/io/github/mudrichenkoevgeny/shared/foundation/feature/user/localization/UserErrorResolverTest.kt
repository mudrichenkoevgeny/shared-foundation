package io.github.mudrichenkoevgeny.shared.foundation.feature.user.localization

import io.github.mudrichenkoevgeny.shared.foundation.core.common.localization.CommonModuleStrings
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import kotlin.test.Test
import kotlin.test.assertEquals

class UserErrorResolverTest {

    private val userStringsEn = UserModuleStrings(locale = "en")
    private val userStringsRu = UserModuleStrings(locale = "ru")
    private val commonStringsEn = CommonModuleStrings(locale = "en")
    private val commonStringsRu = CommonModuleStrings(locale = "ru")

    @Test
    fun `resolveUserLockedMessage handles temporary and indefinite lockout correctly`() {
        val tempEn = userStringsEn.resolveUserLockedMessage(
            lockoutType = AccountLockoutType.TEMPORARY,
            formattedUntil = "2026-03-12 14:30:00 UTC"
        )
        assertEquals("This account has been locked until 2026-03-12 14:30:00 UTC.", tempEn)

        val tempRu = userStringsRu.resolveUserLockedMessage(
            lockoutType = AccountLockoutType.TEMPORARY,
            formattedUntil = "2026-03-12 14:30:00 UTC"
        )
        assertEquals("Этот аккаунт заблокирован до 2026-03-12 14:30:00 UTC.", tempRu)

        val indefEn = userStringsEn.resolveUserLockedMessage(
            lockoutType = AccountLockoutType.INDEFINITE
        )
        assertEquals("This account has been locked.", indefEn)

        val indefRu = userStringsRu.resolveUserLockedMessage(
            lockoutType = AccountLockoutType.INDEFINITE
        )
        assertEquals("Этот аккаунт заблокирован.", indefRu)
    }

    @Test
    fun `resolveUserIdentifierLimitReachedMessage resolves provider display labels`() {
        val emailMsgEn = userStringsEn.resolveUserIdentifierLimitReachedMessage(
            commonStrings = commonStringsEn,
            limit = 2,
            providerRaw = "email"
        )
        assertEquals("The limit of 2 sign-in methods for 'Email' has been reached.", emailMsgEn)

        val emailMsgRu = userStringsRu.resolveUserIdentifierLimitReachedMessage(
            commonStrings = commonStringsRu,
            limit = 2,
            providerRaw = "email"
        )
        assertEquals("Достигнут лимит в 2 способов входа для «Email».", emailMsgRu)

        val phoneMsgEn = userStringsEn.resolveUserIdentifierLimitReachedMessage(
            commonStrings = commonStringsEn,
            limit = 1,
            providerRaw = "phone"
        )
        assertEquals("The limit of 1 sign-in methods for 'Phone' has been reached.", phoneMsgEn)

        val externalMsgEn = userStringsEn.resolveUserIdentifierLimitReachedMessage(
            commonStrings = commonStringsEn,
            limit = 2,
            providerRaw = "google"
        )
        assertEquals("The limit of 2 sign-in methods for 'External Provider' has been reached.", externalMsgEn)

        val externalMsgRu = userStringsRu.resolveUserIdentifierLimitReachedMessage(
            commonStrings = commonStringsRu,
            limit = 2,
            providerRaw = "google"
        )
        assertEquals("Достигнут лимит в 2 способов входа для «Внешний провайдер».", externalMsgRu)
    }

    @Test
    fun `resolveTotalUserIdentifiersLimitReachedMessage handles limit correctly`() {
        val totalWithArgsEn = userStringsEn.resolveTotalUserIdentifiersLimitReachedMessage(limit = 5)
        assertEquals("The maximum total limit of 5 sign-in methods per account has been reached.", totalWithArgsEn)

        val totalWithArgsRu = userStringsRu.resolveTotalUserIdentifiersLimitReachedMessage(limit = 5)
        assertEquals("Достигнут максимальный общий лимит в 5 способов входа на один аккаунт.", totalWithArgsRu)

        val totalWithoutArgsEn = userStringsEn.resolveTotalUserIdentifiersLimitReachedMessage(limit = null)
        assertEquals("The maximum total limit of sign-in methods has been reached.", totalWithoutArgsEn)

        val totalWithoutArgsRu = userStringsRu.resolveTotalUserIdentifiersLimitReachedMessage(limit = null)
        assertEquals("Достигнут общий лимит способов входа.", totalWithoutArgsRu)
    }
}
