package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.methodselection

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.UnlockMethod
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.contract.UserNavigationPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class UnlockMethodSelectionParamsTest {

    @Test
    fun `parse extracts unlockMethod accountLockoutType and temporaryLockoutUntil correctly`() {
        val params = mapOf(
            UserNavigationPaths.UNLOCK_METHOD to "phone",
            UserApiFields.ACCOUNT_LOCKOUT_TYPE to "temporary",
            UserApiFields.TEMPORARY_LOCKOUT_UNTIL to "1700000000000"
        )
        val parsed = UnlockMethodSelectionParams.parse(params)

        assertEquals(UnlockMethod.PHONE, parsed.unlockMethod)
        assertEquals(AccountLockoutType.TEMPORARY, parsed.accountLockoutType)
        assertEquals(1700000000000L, parsed.temporaryLockoutUntil)
    }

    @Test
    fun `parse returns null for missing or invalid values`() {
        val emptyParsed = UnlockMethodSelectionParams.parse(emptyMap())
        assertNull(emptyParsed.unlockMethod)
        assertNull(emptyParsed.accountLockoutType)
        assertNull(emptyParsed.temporaryLockoutUntil)

        val invalid = UnlockMethodSelectionParams.parse(mapOf(UserNavigationPaths.UNLOCK_METHOD to "invalid"))
        assertNull(invalid.unlockMethod)
    }

    @Test
    fun `serialize and toQueryMap produce correct map`() {
        val model = UnlockMethodSelectionParams(
            unlockMethod = UnlockMethod.EMAIL,
            accountLockoutType = AccountLockoutType.INDEFINITE,
            temporaryLockoutUntil = 1700000000000L
        )
        val serialized = model.toQueryMap()

        assertEquals(listOf("email"), serialized[UserNavigationPaths.UNLOCK_METHOD])
        assertEquals(listOf("indefinite"), serialized[UserApiFields.ACCOUNT_LOCKOUT_TYPE])
        assertEquals(listOf("1700000000000"), serialized[UserApiFields.TEMPORARY_LOCKOUT_UNTIL])
        assertEquals(serialized, UnlockMethodSelectionParams.serialize(model))
    }
}
