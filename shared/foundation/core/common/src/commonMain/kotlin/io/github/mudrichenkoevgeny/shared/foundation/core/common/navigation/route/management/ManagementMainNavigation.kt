package io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.management

/**
 * Root navigation route for the staff and management portal.
 */
object ManagementMainNavigation {

    /**
     * Entry main dashboard/hub screen for management.
     *
     * **Screen Parameters:** None.
     *
     * **Primary API:** None.
     *
     * **Secondary Action APIs:** None.
     */
    object Main {
        /** Route path for the management entry dashboard. */
        const val ROUTE = "/"

        /** Parent navigation route (root of the management portal). */
        val PARENT_ROUTE: String? = null
    }
}