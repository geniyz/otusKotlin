package site.geniyz.otus.blackbox.test.action.v1

import site.geniyz.otus.api.v1.models.*
// import site.geniyz.otus.stubs.*

val debug = ObjDebug(mode = RequestDebugMode.STUB, stub = ObjRequestDebugStubs.SUCCESS)

val someCreateObj = ObjCreateObject(
    name    = "Текст1",
    content = "Какой-то тест",
    objType = ObjType.TEXT,
)
