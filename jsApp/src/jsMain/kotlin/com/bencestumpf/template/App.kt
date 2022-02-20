package com.bencestumpf.template

import com.bencestumpf.template.di.initKoin
import dev.fritz2.dom.html.RenderContext

fun RenderContext.App() {
    val koinApp = initKoin()

}

