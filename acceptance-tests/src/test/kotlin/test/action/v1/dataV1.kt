package site.geniyz.otus.blackbox.test.action.v1

import site.geniyz.otus.api.v1.models.*

val debug = ObjDebug(mode = RequestDebugMode.STUB, stub = ObjRequestDebugStubs.SUCCESS)

val someCreateObj = ObjCreateObject(
    name    = "Панграмма1",
    content = "Чушь: гид вёз кэб цапф, юный жмот съел хрящ.",
    objType = ObjType.TEXT,
)
