package site.geniyz.otus.biz.auth

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import site.geniyz.otus.backend.repo.inmemory.RepoInMemory
import site.geniyz.otus.biz.*
import site.geniyz.otus.common.*
import site.geniyz.otus.common.models.*
import site.geniyz.otus.common.permissions.*
import site.geniyz.otus.stubs.*
import kotlin.test.*

/**
 * @crud - экземпляр класса-фасада бизнес-логики
 * @context - контекст, смапленный из транспортной модели запроса
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ObjCrudAuthTest {
    @Test
    fun createSuccessTest() = runTest {
        val userId = AppUserId("123")
        val repo = RepoInMemory()
        val processor = AppProcessor(
            settings = CorSettings(
                repoTest = repo
            )
        )
        val context = AppContext(
            workMode = AppWorkMode.TEST,
            objRequest = AppStubObjs.prepareResult {
                permissionsClient.clear()
                id = AppObjId.NONE
            },
            command = AppCommand.OBJ_CREATE,
            principal = AppPrincipalModel(
                id = userId,
                groups = setOf(
                    AppUserGroups.USER,
                    AppUserGroups.TEST,
                )
            )
        )

        processor.exec(context)
        assertEquals(AppState.FINISHING, context.state)
        with(context.objResponse) {
            assertTrue { id.asString().isNotBlank() }
            assertContains(permissionsClient, AppObjPermissionClient.READ)
            assertContains(permissionsClient, AppObjPermissionClient.UPDATE)
            assertContains(permissionsClient, AppObjPermissionClient.DELETE)
//            assertFalse { permissionsClient.contains(PermissionModel.CONTACT) }
        }
    }

    @Test
    fun readSuccessTest() = runTest {
        val obj = AppStubObjs.getText()
        val userId = obj.authorId
        val objId = obj.id
        val repo = RepoInMemory(initObjs = listOf(obj))
        val processor = AppProcessor(
            settings = CorSettings(
                repoTest = repo
            )
        )
        val context = AppContext(
            command = AppCommand.OBJ_READ,
            workMode = AppWorkMode.TEST,
            objRequest = AppObj(id = objId),
            principal = AppPrincipalModel(
                id = userId,
                groups = setOf(
                    AppUserGroups.USER,
                    AppUserGroups.TEST,
                )
            )
        )
        processor.exec(context)

        println(context.errors)

        assertEquals(AppState.FINISHING, context.state)
        with(context.objResponse) {
            assertTrue { id.asString().isNotBlank() }
            assertContains(permissionsClient, AppObjPermissionClient.READ)
            assertContains(permissionsClient, AppObjPermissionClient.UPDATE)
            assertContains(permissionsClient, AppObjPermissionClient.DELETE)
//            assertFalse { context.responseAd.permissions.contains(PermissionModel.CONTACT) }
        }
    }

}
