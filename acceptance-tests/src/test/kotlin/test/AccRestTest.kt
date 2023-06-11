package site.geniyz.otus.blackbox.test

import site.geniyz.otus.blackbox.fixture.client.RestClient
import io.kotest.core.annotation.Ignored

import site.geniyz.otus.blackbox.docker.KtorDockerCompose
import site.geniyz.otus.blackbox.fixture.BaseFunSpec
import site.geniyz.otus.blackbox.fixture.client.WebSocketClient
import site.geniyz.otus.blackbox.fixture.docker.DockerCompose

@Ignored
open class AccRestTestBase(dockerCompose: DockerCompose) : BaseFunSpec(dockerCompose, {
    val restClient = RestClient(dockerCompose)
    testApiV1(restClient, "rest ")

    val websocketClient = WebSocketClient(dockerCompose)
    testApiV1(websocketClient, "websocket ")
})

class AccRestKtorTest : AccRestTestBase(KtorDockerCompose)
