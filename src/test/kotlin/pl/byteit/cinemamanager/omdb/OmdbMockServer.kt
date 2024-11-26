package pl.byteit.cinemamanager.omdb

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.core.WireMockConfiguration

class OmdbMockServer {

    private val apiKey: String
    private val mockServer: WireMockServer

    constructor(apiKey: String, port: Int) {
        this.apiKey = apiKey
        mockServer = WireMockServer(port)
    }

    constructor(apiKey: String) {
        this.apiKey = apiKey
        mockServer = WireMockServer(WireMockConfiguration.options().dynamicPort())
    }

    fun start() {
        mockServer.start()
    }

    fun stop() {
        mockServer.stop()
    }

    fun port(): Int {
        return mockServer.port()
    }

    fun mockMovieResponse(imdbId: String) {
        mockServer.stubFor(
            get("/?apikey=$apiKey&i=$imdbId")
                .willReturn(
                    aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(getResourceAsString("/omdb/ok-response.json"))
                )
        )

    }

    fun mockApiError(imdbId: String) {
        mockServer.stubFor(
            get("/?apikey=$apiKey&i=$imdbId")
                .willReturn(
                    aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                            """
                                {"Response":"False","Error":"Some error!"}
                            """.trimIndent()
                        )
                )
        )

    }

    fun mockServiceError(imdbId: String) {
        mockServer.stubFor(
            get("/?apikey=$apiKey&i=$imdbId")
                .willReturn(aResponse().withStatus(500))
        )
    }

    private fun getResourceAsString(path: String): String = OmdbMockServer::class.java.getResource(path)?.readText()!!

}
