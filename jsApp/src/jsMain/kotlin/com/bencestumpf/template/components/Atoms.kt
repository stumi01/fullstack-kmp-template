package com.bencestumpf.template.components

import dev.fritz2.components.lineUp
import dev.fritz2.components.stackUp
import dev.fritz2.dom.html.RenderContext

fun RenderContext.Row(child: RenderContext.() -> Unit) =
    lineUp(styling = {
        padding { small }
    }) {
        items(child)
    }

fun RenderContext.Column(child: RenderContext.() -> Unit) =
    stackUp(styling = {
        padding { small }
    }) {
        items(child)
    }
