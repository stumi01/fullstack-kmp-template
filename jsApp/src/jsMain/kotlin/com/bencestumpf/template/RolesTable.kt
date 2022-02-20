package com.bencestumpf.template

import com.bencestumpf.template.components.Column
import com.bencestumpf.template.components.Row
import com.bencestumpf.template.models.Role
import dev.fritz2.binding.storeOf
import dev.fritz2.components.clickButton
import dev.fritz2.components.inputField
import dev.fritz2.dom.html.RenderContext
import kotlinx.coroutines.flow.Flow

fun RenderContext.RolesTable(
    roles: Flow<List<Role>>,
    addRole: (String) -> Unit
) {
    val roleName = storeOf("")
    val roleButton = storeOf(Unit)

    Row {
        inputField(value = roleName,
            styling = {
                width { "200px" }
            }) {
            placeholder("Role")
        }
        clickButton {
            text("Add")
        }.map {
            addRole(roleName.current)
            roleName.update.invoke("")
            Unit
        } handledBy roleButton.update
    }


    Column {
        roles.render { roles ->
            roles.forEach {
                p {
                    +it.name
                }
            }
        }
    }
}
