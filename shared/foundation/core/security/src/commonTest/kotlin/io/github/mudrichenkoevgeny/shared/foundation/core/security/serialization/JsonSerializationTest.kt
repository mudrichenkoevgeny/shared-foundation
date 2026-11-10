package io.github.mudrichenkoevgeny.shared.foundation.core.security.serialization

import io.github.mudrichenkoevgeny.shared.foundation.core.common.serialization.FoundationJson
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.accountlockout.AccountLockoutPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.iprestriction.IpRestrictionPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.otpconfirmation.OtpConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.passwordpolicy.ManagementPasswordPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.passwordpolicy.OpenPasswordPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.ManagementSecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.OpenSecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.totpsetup.TotpSetupPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.verifytotp.VerifyTotpPayload
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Unit tests verifying JSON serialization and deserialization contracts for core/security payloads.
 */
public class JsonSerializationTest {
    @Test
    public fun testTotpSetupPayloadSerialization() {
        val payload = TotpSetupPayload(
            secretKey = "JBSWY3DPEHPK3PXP",
            otpAuthUrl = "otpauth://totp/Example:user@example.com?secret=JBSWY3DPEHPK3PXP",
            mfaToken = "mfa_token_12345"
        )

        val serialized = FoundationJson.encodeToString(payload)

        assertTrue(serialized.contains("\"totp_secret_key\":\"JBSWY3DPEHPK3PXP\""))
        assertTrue(
            serialized.contains(
                "\"totp_otp_auth_url\":\"otpauth://totp/Example:user@example.com?secret=JBSWY3DPEHPK3PXP\""
            )
        )
        assertTrue(serialized.contains("\"mfa_token\":\"mfa_token_12345\""))

        val deserialized = FoundationJson.decodeFromString<TotpSetupPayload>(serialized)
        assertEquals(payload, deserialized)
    }

    @Test
    public fun testVerifyTotpPayloadSerialization() {
        val payload = VerifyTotpPayload(
            mfaToken = "mfa_token_12345",
            code = "123456"
        )

        val serialized = FoundationJson.encodeToString(payload)

        assertTrue(serialized.contains("\"mfa_token\":\"mfa_token_12345\""))
        assertTrue(serialized.contains("\"totp_code\":\"123456\""))

        val deserialized = FoundationJson.decodeFromString<VerifyTotpPayload>(serialized)
        assertEquals(payload, deserialized)
    }

    @Test
    public fun testOpenSecuritySettingsPayloadSerialization() {
        val passwordPolicy = OpenPasswordPolicyPayload(
            minLength = 8,
            requireLetter = true,
            requireUpperCase = true,
            requireLowerCase = true,
            requireDigit = true,
            requireSpecialChar = false
        )

        val otpConfirmation = OtpConfirmationPayload(
            retryAfterSeconds = 60,
            numberOfSymbols = 6,
            expirationSeconds = 300
        )

        val payload = OpenSecuritySettingsPayload(
            passwordPolicy = passwordPolicy,
            otpConfirmation = otpConfirmation
        )

        val serialized = FoundationJson.encodeToString(payload)

        assertTrue(serialized.contains("\"open_password_policy\":"))
        assertTrue(serialized.contains("\"otp_confirmation\":"))
        assertTrue(serialized.contains("\"min_length\":8"))
        assertTrue(serialized.contains("\"number_of_symbols\":6"))

        val deserialized = FoundationJson.decodeFromString<OpenSecuritySettingsPayload>(serialized)
        assertEquals(payload, deserialized)
    }

    @Test
    public fun testManagementSecuritySettingsPayloadSerialization() {
        val passwordPolicy = ManagementPasswordPolicyPayload(
            minLength = 12,
            requireLetter = true,
            requireUpperCase = true,
            requireLowerCase = true,
            requireDigit = true,
            requireSpecialChar = true,
            commonPasswords = setOf("123456", "password")
        )

        val otpConfirmation = OtpConfirmationPayload(
            retryAfterSeconds = 60,
            numberOfSymbols = 6,
            expirationSeconds = 300
        )

        val accountLockout = AccountLockoutPolicyPayload(
            maxFailedPasswordAttempts = 5,
            maxFailedOtpAttempts = 3,
            maxFailedTotpAttempts = 3,
            failedAttemptsWindowSeconds = 900,
            lockoutDurationSeconds = 1800,
            indefiniteLockoutThreshold = 3,
            isSelfServiceUnlockEnabled = true
        )

        val ipRestriction = IpRestrictionPolicyPayload(
            isBlacklistEnabled = true,
            blacklist = listOf("192.168.1.100"),
            isWhitelistEnabled = false,
            whitelist = emptyList()
        )

        val payload = ManagementSecuritySettingsPayload(
            recentAuthenticationValiditySecondsForOpenUser = 300,
            recentAuthenticationValiditySecondsForManagementUser = 120,
            passwordPolicy = passwordPolicy,
            otpConfirmation = otpConfirmation,
            accountLockoutPolicy = accountLockout,
            accountLockoutCheckIntervalSeconds = 60,
            openIpRestrictionPolicy = ipRestriction,
            managementIpRestrictionPolicy = ipRestriction,
            mfaTokenExpirationSeconds = 600,
            maxRequestsPerPeriod = 100,
            rateLimitPeriodSeconds = 60,
            refreshTokenRotationGracePeriodSeconds = 10
        )

        val serialized = FoundationJson.encodeToString(payload)

        assertTrue(serialized.contains("\"recent_authentication_validity_seconds_for_open_user\":300"))
        assertTrue(serialized.contains("\"recent_authentication_validity_seconds_for_management_user\":120"))
        assertTrue(serialized.contains("\"management_password_policy\":"))
        assertTrue(serialized.contains("\"account_lockout_policy\":"))
        assertTrue(serialized.contains("\"open_ip_restriction_policy\":"))
        assertTrue(serialized.contains("\"management_ip_restriction_policy\":"))

        val deserialized = FoundationJson.decodeFromString<ManagementSecuritySettingsPayload>(serialized)
        assertEquals(payload, deserialized)
    }

    @Test
    public fun testAccountLockoutPolicyPayloadSerialization() {
        val payload = AccountLockoutPolicyPayload(
            maxFailedPasswordAttempts = 5,
            maxFailedOtpAttempts = 3,
            maxFailedTotpAttempts = 3,
            failedAttemptsWindowSeconds = 900,
            lockoutDurationSeconds = 1800,
            indefiniteLockoutThreshold = 3,
            isSelfServiceUnlockEnabled = true
        )

        val serialized = FoundationJson.encodeToString(payload)

        assertTrue(serialized.contains("\"max_failed_password_attempts\":5"))
        assertTrue(serialized.contains("\"max_failed_otp_attempts\":3"))
        assertTrue(serialized.contains("\"max_failed_totp_attempts\":3"))
        assertTrue(serialized.contains("\"failed_attempts_window_seconds\":900"))
        assertTrue(serialized.contains("\"lockout_duration_seconds\":1800"))
        assertTrue(serialized.contains("\"indefinite_lockout_threshold\":3"))
        assertTrue(serialized.contains("\"is_self_service_unlock_enabled\":true"))

        val deserialized = FoundationJson.decodeFromString<AccountLockoutPolicyPayload>(serialized)
        assertEquals(payload, deserialized)
    }

    @Test
    public fun testIpRestrictionPolicyPayloadSerialization() {
        val payload = IpRestrictionPolicyPayload(
            isBlacklistEnabled = true,
            blacklist = listOf("10.0.0.1", "192.168.1.0/24"),
            isWhitelistEnabled = true,
            whitelist = listOf("127.0.0.1")
        )

        val serialized = FoundationJson.encodeToString(payload)

        assertTrue(serialized.contains("\"is_blacklist_enabled\":true"))
        assertTrue(serialized.contains("\"blacklist\":[\"10.0.0.1\",\"192.168.1.0/24\"]"))
        assertTrue(serialized.contains("\"is_whitelist_enabled\":true"))
        assertTrue(serialized.contains("\"whitelist\":[\"127.0.0.1\"]"))

        val deserialized = FoundationJson.decodeFromString<IpRestrictionPolicyPayload>(serialized)
        assertEquals(payload, deserialized)
    }
}
