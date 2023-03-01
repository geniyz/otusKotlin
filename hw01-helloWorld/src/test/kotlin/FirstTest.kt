package site.geniyz.otus

import kotlin.test.Test
import kotlin.test.*

class TestSimple {
    @Test
    fun firstTest() {
        val a = 3
        val b = a * a
        assertEquals(b, a * a)
        assertFalse { a * a < b }
    }
}
