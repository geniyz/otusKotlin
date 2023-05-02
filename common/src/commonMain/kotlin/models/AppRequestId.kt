package site.geniyz.otus.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class AppRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = AppRequestId("")
    }
}