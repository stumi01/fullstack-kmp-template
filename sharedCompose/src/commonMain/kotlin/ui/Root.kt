package ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.bencestumpf.template.di.initKoin

interface RootComponent {

    val routerState: Value<RouterState<*, Child>>

    sealed class Child {
        data class DashboardRoute(val component: DashboardComponent) : Child()
    }
}

class RootComponentImpl(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    init {
        initKoin()
    }

    private val router =
        router<Config, RootComponent.Child>(
            initialConfiguration = Config.Dashboard,
            handleBackButton = true, // Pop the back stack on back button press
            childFactory = ::createChild
        )

    override val routerState: Value<RouterState<*, RootComponent.Child>> = router.state

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child =
        when (config) {
            Config.Dashboard -> RootComponent.Child.DashboardRoute(dashboard(componentContext))
        }

    private fun dashboard(componentContext: ComponentContext): DashboardComponent =
        DashboardComponentImpl(
            componentContext = componentContext,
            onGoToNext = { /* router.push(Config.Next) */ },
        )

    private sealed class Config : Parcelable {
        @Parcelize
        object Dashboard : Config()
    }
}

@Composable
fun RootUI(rootComponent: RootComponent) =
    Children(
        rootComponent.routerState
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.DashboardRoute -> DashboardUi(child.component)
        }
    }
