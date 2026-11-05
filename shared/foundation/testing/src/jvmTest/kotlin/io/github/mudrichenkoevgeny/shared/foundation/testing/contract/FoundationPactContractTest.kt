package io.github.mudrichenkoevgeny.shared.foundation.testing.contract

import au.com.dius.pact.consumer.MockServer
import au.com.dius.pact.consumer.dsl.PactDslWithProvider
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt
import au.com.dius.pact.consumer.junit5.PactTestFor
import au.com.dius.pact.core.model.RequestResponsePact
import au.com.dius.pact.core.model.annotations.Pact
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.net.HttpURLConnection
import java.net.URI
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExtendWith(PactConsumerTestExt::class)
@PactTestFor(providerName = "BackendApiProvider")
public class FoundationPactContractTest {
    @Pact(consumer = "SharedFoundationSDK")
    public fun createLoginPact(builder: PactDslWithProvider): RequestResponsePact {
        val headers = mapOf("Content-Type" to "application/json")
        return builder
            .given("User exists and credentials are valid")
            .uponReceiving("A request to login by email")
            .path("/api/v1/auth/login")
            .method("POST")
            .headers(headers)
            .willRespondWith()
            .status(200)
            .headers(headers)
            .body(
                """
                {
                  "access_token": "mock.access.jwt.token",
                  "refresh_token": "mock.refresh.jwt.token",
                  "expires_in": 3600
                }
                """.trimIndent()
            ).toPact()
    }

    @Pact(consumer = "SharedFoundationSDK")
    public fun createSelfUserPact(builder: PactDslWithProvider): RequestResponsePact {
        val headers = mapOf("Authorization" to "Bearer mock.jwt.token")
        val responseHeaders = mapOf("Content-Type" to "application/json")
        return builder
            .given("User is authenticated")
            .uponReceiving("A request to fetch self user details")
            .path("/api/v1/user/self")
            .method("GET")
            .headers(headers)
            .willRespondWith()
            .status(200)
            .headers(responseHeaders)
            .body(
                """
                {
                  "id": "00000000-0000-4000-8000-000000000001",
                  "display_name": "Test User",
                  "nickname": "testuser",
                  "account_status": "ACTIVE"
                }
                """.trimIndent()
            ).toPact()
    }

    @Test
    @PactTestFor(pactMethod = "createLoginPact")
    public fun testLoginPactContract(mockServer: MockServer) {
        val connection = URI("${mockServer.getUrl()}/api/v1/auth/login").toURL().openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")

        assertEquals(200, connection.responseCode)
        val responseBody = connection.inputStream.bufferedReader().readText()
        assertTrue(responseBody.contains("mock.access.jwt.token"))
    }

    @Test
    @PactTestFor(pactMethod = "createSelfUserPact")
    public fun testSelfUserPactContract(mockServer: MockServer) {
        val connection = URI("${mockServer.getUrl()}/api/v1/user/self").toURL().openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.setRequestProperty("Authorization", "Bearer mock.jwt.token")

        assertEquals(200, connection.responseCode)
        val responseBody = connection.inputStream.bufferedReader().readText()
        assertTrue(responseBody.contains("Test User"))
    }
}
