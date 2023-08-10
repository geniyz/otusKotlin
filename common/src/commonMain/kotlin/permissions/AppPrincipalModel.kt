package site.geniyz.otus.common.permissions

import site.geniyz.otus.common.models.AppUserId

data class AppPrincipalModel(
    val id: AppUserId = AppUserId.NONE,
    val fname: String = "",
    val mname: String = "",
    val lname: String = "",
    val groups: Set<AppUserGroups> = emptySet()
) {
    companion object {
        val NONE = AppPrincipalModel()
    }
}
