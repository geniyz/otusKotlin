package site.geniyz.otus.common.models

import kotlinx.datetime.Instant
import site.geniyz.otus.common.NONE

data class AppTag(
    var id:        AppTagId = AppTagId.NONE,
    var name:      String   = "",             // наименование
    var code:      String   = "",             // пермалинк
    var createdAt: Instant  = Instant.NONE,   // дата-время создания
    var updatedAt: Instant  = Instant.NONE,   // дата-время последнего изменения
    var objQty:    Int      = 0,              // кол-во объектов с такой меткою

    var lock:      AppLock  = AppLock.NONE,   // идентификатор версии метки для позитивной блокировки
){
    fun isEmpty() = this == NONE

    companion object {
        val NONE = AppTag()
    }

}
