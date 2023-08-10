package site.geniyz.otus.biz.permissions

import site.geniyz.otus.auth.*
import site.geniyz.otus.common.AppContext
import site.geniyz.otus.common.models.AppState
import site.geniyz.otus.cor.*

fun ICorChainDsl<AppContext>.frontPermissions(title: String) = worker {
    this.title = title
    description = "Вычисление разрешений пользователей для фронтенда"

    on { state == AppState.RUNNING }

    handle {
        objRepoDone.permissionsClient.addAll(
            resolveFrontPermissions(
                permissionsChain,
                // Повторно вычисляем отношения, поскольку они могли измениться при выполении операции
                objRepoDone.resolveRelationsTo(principal)
            )
        )

        for (o in objsRepoDone) {
            o.permissionsClient.addAll(
                resolveFrontPermissions(
                    permissionsChain,
                    o.resolveRelationsTo(principal)
                )
            )
        }
    }
}
