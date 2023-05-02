package site.geniyz.otus.common.models

enum class AppCommand {
    NONE,
    // Obj:
    OBJ_CREATE,
    OBJ_READ,
    OBJ_UPDATE,
    OBJ_DELETE,
    OBJ_SEARCH,
    OBJ_LIST_TAGS,
    OBJ_SET_TAGS,
    // Tag:
    TAG_DELETE,
    TAG_SEARCH,
    TAG_LIST_OBJS,
}
