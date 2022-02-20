package ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import ui.atoms.DButton
import ui.atoms.DColumn

interface DashboardComponent {

    fun onGoToNext()
}

class DashboardComponentImpl(
    componentContext: ComponentContext,
    private val onGoToNext: () -> Unit,
) : DashboardComponent, ComponentContext by componentContext {

    override fun onGoToNext() = onGoToNext.invoke()
}

@Composable
fun DashboardUi(component: DashboardComponent) {
    DColumn {
        item {
            DButton(component::onGoToNext, "NEXT")
        }
    }
}
