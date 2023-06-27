package site.geniyz.otus.logging.jvm

import ch.qos.logback.classic.Logger
import org.slf4j.LoggerFactory
import site.geniyz.otus.logging.common.IAppLogWrapper
import kotlin.reflect.KClass

/**
 * Generate internal AppLogContext logger
 *
 * @param logger Logback instance from [LoggerFactory.getLogger()]
 */
fun appLoggerLogback(logger: Logger): IAppLogWrapper = AppLogWrapperLogback(
    logger = logger,
    loggerId = logger.name,
)

fun appLoggerLogback(clazz: KClass<*>): IAppLogWrapper = appLoggerLogback(LoggerFactory.getLogger(clazz.java) as Logger)
@Suppress("unused")
fun appLoggerLogback(loggerId: String): IAppLogWrapper = appLoggerLogback(LoggerFactory.getLogger(loggerId) as Logger)
