package site.geniyz.otus.app.v1

import io.ktor.websocket.*

import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.decodeFromString

import site.geniyz.otus.api.v1.apiV1Mapper
import site.geniyz.otus.api.v1.apiV1ResponseSerialize
import site.geniyz.otus.api.v1.models.IRequest
import site.geniyz.otus.app.AppSettings
import site.geniyz.otus.biz.AppProcessor
import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.helpers.isUpdatableCommand
import site.geniyz.otus.common.models.AppCommand
import site.geniyz.otus.mappers.v1.fromTransport.fromTransport
import site.geniyz.otus.mappers.v1.toTransport.toTransport
import site.geniyz.otus.mappers.v1.toTransport.toTransportInit

class WSController {
    private val mutex = Mutex()
    private val sessions = mutableSetOf<WebSocketSession>() // активные соединения

    suspend fun handle(
        session: WebSocketSession,
        appSettings: AppSettings,
    ) {
        val logger = appSettings.loggerProvider.logger(WSController::class)

        mutex.withLock {
            sessions.add(session) // добавить текущее соединение к имеющимся
        }

        // послать инициализационные данные новому поделючению:
        session.outgoing.send( Frame.Text( apiV1ResponseSerialize(AppContext().toTransportInit()) ) )

        session.incoming.receiveAsFlow().mapNotNull { it ->
            val frame = it as? Frame.Text ?: return@mapNotNull

            val jsonStr = frame.readText()

            AppProcessor().process(
                logger = logger,
                logId = "webSocket",
                command = AppCommand.NONE,
                fromTransport = { ctx ->
                    val request = apiV1Mapper.decodeFromString<IRequest>(jsonStr)
                    ctx.fromTransport(request)
                },
                sendResponse = { ctx ->
                    try {
                        val rez = apiV1ResponseSerialize(ctx.toTransport())

                        if (ctx.isUpdatableCommand()) { // если предполагается изменение данных — послать сообщение о изменении всем
                            mutex.withLock {
                                sessions.forEach {
                                    it.send(Frame.Text(rez))
                                }
                            }
                        } else { // если это чтение — то отправить только тому, кто запросил данные
                            session.outgoing.send(Frame.Text(rez))
                        }
                    } catch (_: ClosedReceiveChannelException) {
                        mutex.withLock {
                            // TODO:
                            // в Маркетплейсе почему-то «sessions.clear()»
                            // не понимаю, зачем очищать/закрывать все соединения, если ошибка только где-то в одном.
                            // Хотя в целом, можно надеяться на корректные переподключения
                            // Но ИМХО корректно — закрыть/удалить только одно текущее соединение
                            sessions.remove(session)
                        }
                    }
                })
        }.collect()
    }
}
