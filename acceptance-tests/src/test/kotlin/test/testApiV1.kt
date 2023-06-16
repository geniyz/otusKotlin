package site.geniyz.otus.blackbox.test

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import site.geniyz.otus.api.v1.models.ObjSearchFilter
import site.geniyz.otus.api.v1.models.ObjUpdateObject
import site.geniyz.otus.api.v1.models.ObjType
import site.geniyz.otus.blackbox.fixture.client.Client
import site.geniyz.otus.blackbox.test.action.v1.*

fun FunSpec.testApiV1(client: Client, prefix: String = "") {
    context("${prefix}v1") {
        test("Create Obj ok") {
            client.createObj()
        }

        test("Read Obj ok") {
            val created = client.createObj()
            client.readObj(created.id).asClue {
                it shouldBe created
            }
        }

        test("Update Obj ok") {
            val created = client.createObj()
            client.updateObj(created.id, ObjUpdateObject(name = "Текст1"))
            client.readObj(created.id) {
                // TODO раскомментировать, когда будет реальный реп
                //it.obj?.name shouldBe "Текст1"
                //it.obj?.content shouldBe someCreateObj.content
            }
        }

        test("Delete Obj ok") {
            val created = client.createObj()
            client.deleteObj(created.id)
            client.readObj(created.id) {
                // it should haveError("not-found") TODO раскомментировать, когда будет реальный реп
            }
        }

        test("Search Obj ok") {
            val created1 = client.createObj(someCreateObj.copy(name = "Текст1"))
            // val created2 = client.createObj(someCreateObj.copy(name = "Текст2"))

            withClue("Search Текст") {
                val results = client.searchObj(search = ObjSearchFilter(searchString = "Текст"))
                // TODO раскомментировать, когда будет реальный реп
                // results shouldHaveSize 2
                // results shouldExist { it.name == "Текст1" }
                // results shouldExist { it.name == "Текст2" }
            }

            withClue("Search Текст2") {
                client.searchObj(search = ObjSearchFilter(searchString = "Текст1"))
                // TODO раскомментировать, когда будет реальный реп
                // .shouldExistInOrder({ it.name == "Текст2" })
            }
        }

    }

}
