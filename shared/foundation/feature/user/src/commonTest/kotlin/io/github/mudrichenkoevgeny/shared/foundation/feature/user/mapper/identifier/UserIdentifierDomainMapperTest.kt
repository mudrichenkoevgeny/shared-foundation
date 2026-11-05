package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.identifier

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierInternal
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Instant

/**
 * Unit tests verifying domain projection mapping for user identifiers (Internal -> Private -> Summary).
 */
class UserIdentifierDomainMapperTest {
    private val testId: UserIdentifierId = UserIdentifierId.generate()
    private val testUserId: UserId = UserId.generate()
    private val testCreatedAt: Instant = Instant.fromEpochMilliseconds(1700000000000L)

    private val testInternal: UserIdentifierInternal =
        UserIdentifierInternal(
            id = testId,
            userId = testUserId,
            userAuthProvider = UserAuthProvider.EMAIL,
            identifier = "john.doe@example.com",
            passwordHash = null,
            displayName = "Primary Email",
            externalProviderEmail = "john.doe@gmail.com",
            createdAt = testCreatedAt,
            updatedAt = null
        )

    @Test
    fun testInternalToPrivateMapping() {
        val privateModel: UserIdentifierPrivate = testInternal.toUserIdentifierPrivate(isSensitiveValuesMasked = false)

        assertEquals(testId, privateModel.id)
        assertEquals(testUserId, privateModel.userId)
        assertEquals(UserAuthProvider.EMAIL, privateModel.userAuthProvider)
        assertEquals("john.doe@example.com", privateModel.identifier)
        assertEquals("Primary Email", privateModel.displayName)
        assertEquals("john.doe@gmail.com", privateModel.externalProviderEmail)
        assertEquals(false, privateModel.isSensitiveValuesMasked)
        assertEquals(testCreatedAt, privateModel.createdAt)
    }

    @Test
    fun testPrivateToSummaryMapping() {
        val privateModel: UserIdentifierPrivate = testInternal.toUserIdentifierPrivate(isSensitiveValuesMasked = false)
        val summaryModel: UserIdentifierSummary = privateModel.toUserIdentifierSummary()

        assertEquals(testId, summaryModel.id)
        assertEquals(testUserId, summaryModel.userId)
        assertEquals(UserAuthProvider.EMAIL, summaryModel.userAuthProvider)
        assertEquals("john.doe@example.com", summaryModel.identifier)
        assertEquals("Primary Email", summaryModel.displayName)
    }

    @Test
    fun testInternalToSummaryMappingDirect() {
        val summaryModel: UserIdentifierSummary = testInternal.toUserIdentifierSummary()

        assertEquals(testId, summaryModel.id)
        assertEquals(testUserId, summaryModel.userId)
        assertEquals(UserAuthProvider.EMAIL, summaryModel.userAuthProvider)
        assertEquals("john.doe@example.com", summaryModel.identifier)
        assertEquals("Primary Email", summaryModel.displayName)
    }
}
