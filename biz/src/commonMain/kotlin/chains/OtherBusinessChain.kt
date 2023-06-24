package site.geniyz.otus.biz

import site.geniyz.otus.biz.workers.*

import site.geniyz.otus.common.AppContext

import site.geniyz.otus.cor.*

val OtherBusinessChain: ICorExec<AppContext>
    get() = rootChain<AppContext> {
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }.build()