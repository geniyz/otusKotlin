package site.geniyz.otus.common.models

import kotlinx.datetime.Instant
import site.geniyz.otus.common.NONE

data class AppObj(
    var id:        AppObjId   = AppObjId.NONE,
    var name:      String     = "",                // наименование
    var content:   String     = "",                // содержимое
    var objType:   AppObjType = AppObjType.NONE,   // тип объекта
    var authorId:  AppUserId  = AppUserId.NONE,    // автор-владелец
    var createdAt: Instant    = Instant.NONE,      // дата-время создания
    var updatedAt: Instant    = Instant.NONE,      // дата-время последнего изменения

    var lock:      AppLock    = AppLock.NONE,      // идентификатор версии объекта для позитивной блокировки

){
    fun isEmpty() = this == NONE

    companion object {
        val NONE = AppObj()
    }

}
