package ink.bluecloud.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import tornadofx.*
import tornadofx.Controller

abstract class Controller: KoinComponent,Controller() {
    abstract fun View.initUi()

    private val ioScope by inject<CoroutineScope>(named("ioScope"))
    private val uiScope by inject<CoroutineScope>(named("uiScope"))

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

}