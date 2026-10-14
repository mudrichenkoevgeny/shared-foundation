package io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route

/**
 * Formats a fully qualified URL for this navigation route using the provided [baseUrl].
 *
 * Accepts any valid base URL string including protocol and domain or port
 * (e.g. "https://example.com" or "http://localhost:3000").
 *
 * @param baseUrl Base URL string including protocol and domain.
 * @return Fully formatted and URL-encoded URL string with query parameters if present.
 */
fun NavigationRoute.toFullUrl(baseUrl: String): String {
    val cleanBaseUrl = baseUrl.trimEnd('/')
    val cleanRoute = route.trim('/')
    val fullPathUrl = if (cleanRoute.isNotEmpty()) "$cleanBaseUrl/$cleanRoute" else cleanBaseUrl

    if (this !is ParameterizedNavigationRoute<*>) return fullPathUrl

    val queryParams = params.toQueryMap().flatMap { (key, values) ->
        values.filter { it.isNotBlank() }.map { value ->
            "${key.encodeUriQueryComponent()}=${value.encodeUriQueryComponent()}"
        }
    }
    if (queryParams.isEmpty()) return fullPathUrl

    return "$fullPathUrl?${queryParams.joinToString("&")}"
}

/**
 * Percent-encodes a URI query parameter component according to RFC 3986.
 */
private fun String.encodeUriQueryComponent(): String {
    val allowed = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-._~"
    val sb = StringBuilder()
    val bytes = this.encodeToByteArray()
    for (b in bytes) {
        val c = b.toInt().and(0xFF).toChar()
        if (c in allowed) {
            sb.append(c)
        } else {
            val hex = b.toInt().and(0xFF).toString(16).uppercase()
            sb.append('%')
            if (hex.length == 1) sb.append('0')
            sb.append(hex)
        }
    }
    return sb.toString()
}
