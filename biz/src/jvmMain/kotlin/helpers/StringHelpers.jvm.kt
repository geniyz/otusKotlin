package site.geniyz.otus.biz.helpers

import com.github.radist_nt.iuliia.Iuliia

actual fun String.transliterate(): String = Iuliia.translate(this, Iuliia.WIKIPEDIA)
