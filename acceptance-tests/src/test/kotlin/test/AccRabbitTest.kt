//// Задел на RabbitMQ — пока его нет
//package site.geniyz.otus.blackbox.test
//
//import site.geniyz.otus.blackbox.docker.RabbitDockerCompose
//import site.geniyz.otus.blackbox.fixture.BaseFunSpec
//import site.geniyz.otus.blackbox.fixture.client.RabbitClient
//
//class AccRabbitTest : BaseFunSpec(RabbitDockerCompose, {
//    val client = RabbitClient(RabbitDockerCompose)
//
//    testApiV1(client)
//
//})
