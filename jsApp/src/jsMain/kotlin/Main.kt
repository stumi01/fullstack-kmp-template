import com.bencestumpf.template.App
import com.bencestumpf.template.di.initKoin
import dev.fritz2.dom.html.render
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.core.component.KoinComponent


object AppDependencies : KoinComponent {
    init {
        initKoin()
    }
}

@InternalCoroutinesApi
fun main() {
    render {
        App()
    }
}
