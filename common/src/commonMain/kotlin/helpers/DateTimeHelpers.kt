package site.geniyz.otus.common.helpers

import kotlinx.datetime.Instant
import site.geniyz.otus.common.NONE

fun Instant.asString()= toString()

fun String.asInstant(): Instant = this.toLongOrNull()?.let{
    Instant.fromEpochMilliseconds( it )
} ?: Instant.NONE
