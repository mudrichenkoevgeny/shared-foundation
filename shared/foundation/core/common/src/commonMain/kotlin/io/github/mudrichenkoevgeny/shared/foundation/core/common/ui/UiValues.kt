package io.github.mudrichenkoevgeny.shared.foundation.core.common.ui

/**
 * Non-localized UI values, including input field placeholders and static format hints.
 */
public object UiValues {
    /**
     * Non-localized input field sample placeholders.
     */
    public object Placeholder {
        /**
         * Default placeholder for email input fields.
         */
        public const val EMAIL: String = "user@example.com"

        /**
         * Default placeholder for 6-digit TOTP confirmation code input fields.
         */
        public const val TOTP_CODE: String = "000000"
    }
}
