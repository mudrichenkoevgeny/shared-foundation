package io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params

interface NavigationScreenParams {
    fun toQueryMap(): Map<String, List<String>>
}