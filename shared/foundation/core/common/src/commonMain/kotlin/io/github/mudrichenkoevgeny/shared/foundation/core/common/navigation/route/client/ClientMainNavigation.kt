package io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.client

/**
 * Root navigation route for the client application.
 */
object ClientMainNavigation {

    /**
     * Entry home/main screen for clients.
     *
     * **Screen Parameters:** None.
     *
     * **Primary API:** None.
     *
     * **Secondary Action APIs:** None.
     */
    object Main {
        /** Route path for the client entry home screen. */
        const val ROUTE = "/"

        /** Parent navigation route (root of the client app). */
        val PARENT_ROUTE: String? = null
    }
}