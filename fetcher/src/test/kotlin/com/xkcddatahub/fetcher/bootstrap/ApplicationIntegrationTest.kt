package com.xkcddatahub.fetcher.bootstrap

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import com.xkcddatahub.fetcher.adapters.output.AbstractIntegrationTest
import com.xkcddatahub.fetcher.application.ports.WebComicsPort
import io.kotest.matchers.shouldNotBe
import io.ktor.server.config.MapApplicationConfig
import io.ktor.server.testing.testApplication
import kotlinx.coroutines.runBlocking
import org.awaitility.kotlin.await
import org.awaitility.kotlin.untilNotNull
import org.junit.jupiter.api.BeforeEach
import org.koin.java.KoinJavaComponent.inject
import kotlin.test.Test

@WireMockTest
class ApplicationIntegrationTest(
    private val wmRuntimeInfo: WireMockRuntimeInfo,
) : AbstractIntegrationTest() { // /}, KoinTest {
    @BeforeEach
    fun setUp() {
        wmRuntimeInfo.wireMock.resetRequests()
    }

    @Test
    fun `should fetch comics and store it in the database`() =
        testApplication {
            // Given
            environment {
                withLastComicId()
                withComic(1)

                config =
                    MapApplicationConfig().apply {
                        config.keys().forEach { put(it, config.property(it).toString()) }
                        put("xkcd.baseUrl", wmRuntimeInfo.httpsBaseUrl)
                    }
            }

            // When
            application {
                module()
            }

            startApplication()

            // Then

            val repository: WebComicsPort by inject(WebComicsPort::class.java)
            val comics =
                await untilNotNull { runBlocking { repository.getComicsById(2940) } }
            comics shouldNotBe null
        }

    private fun withLastComicId() {
        stubFor(
            WireMock.get("/info.0.json")
                .willReturn(
                    WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("/responses/xkcd/latest-index.json"),
                ),
        )
    }

    private fun withComic(id: Int) {
        stubFor(
            WireMock.get("/$id/info.0.json")
                .willReturn(
                    WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("/responses/xkcd/webcomics.json"),
                ),
        )
    }
}
