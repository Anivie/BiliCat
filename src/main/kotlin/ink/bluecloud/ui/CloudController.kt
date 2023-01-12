package ink.bluecloud.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import tornadofx.*

abstract class CloudController<T: View>: KoinComponent {
    abstract fun initUi(view: T)

    private val ioScope by inject<CoroutineScope>(named("ioScope"))
    private val uiScope by inject<CoroutineScope>(named("uiScope"))

    internal inline val ioContext
        get() = ioScope.coroutineContext

    internal inline val uiContext
        get() = uiScope.coroutineContext

    /**
     * 运行在JavaFx协程上
     * */
    protected fun ui(block: suspend CoroutineScope.() -> Unit) {
        uiScope.launch {
            block()
        }
    }

    /**
     * 运行在IO协程上
     * */
    protected fun io(block: suspend CoroutineScope.() -> Unit) {
        ioScope.launch {
            block()
        }
    }


    protected suspend fun toUI(block: suspend CoroutineScope.() -> Unit) {
        withContext(uiContext) {
            block()
        }
    }
    protected suspend fun toIO(block: suspend CoroutineScope.() -> Unit) {
        withContext(ioContext) {
            block()
        }
    }
}