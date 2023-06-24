package site.geniyz.otus.biz

import site.geniyz.otus.biz.workers.*

import site.geniyz.otus.common.AppContext

import site.geniyz.otus.cor.*

val AnyBusinessChain: ICorExec<AppContext>
    get() = rootChain<AppContext> {
                initStatus("Инициализация статуса")
            }.build()