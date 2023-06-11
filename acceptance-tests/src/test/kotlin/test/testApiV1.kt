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
            client.updateObj(created.id, ObjUpdateObject(name = "Selling Nut"))
            client.readObj(created.id) {
                // TODO раскомментировать, когда будет реальный реп
                //it.obj?.name shouldBe "Selling Nut"
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
            val created1 = client.createObj(someCreateObj.copy(name = "Selling Bolt"))
            val created2 = client.createObj(someCreateObj.copy(name = "Selling Nut"))

            withClue("Search Selling") {
                val results = client.searchObj(search = ObjSearchFilter(searchString = "Selling"))
                // TODO раскомментировать, когда будет реальный реп
                // results shouldHaveSize 2
                // results shouldExist { it.name == "Selling Bolt" }
                // results shouldExist { it.name == "Selling Nut" }
            }

            withClue("Search Bolt") {
                client.searchObj(search = ObjSearchFilter(searchString = "Bolt"))
                // TODO раскомментировать, когда будет реальный реп
                // .shouldExistInOrder({ it.name == "Selling Bolt" })
            }
        }

    }

}
