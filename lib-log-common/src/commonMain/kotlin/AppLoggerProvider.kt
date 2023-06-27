package site.geniyz.otus.logging.common

import kotlin.reflect.KClass
import kotlin.reflect.KFunction

class AppLoggerProvider(
    private val provider: (String) -> IAppLogWrapper = { IAppLogWrapper.DEFAULT }
) {
    fun logger(loggerId: String) = provider(loggerId)
    fun logger(clazz: KClass<*>) = provider(clazz.qualifiedName ?: clazz.simpleName ?: "(unknown)")
    fun logger(function: KFunction<*>) = provider(function.name)
}
