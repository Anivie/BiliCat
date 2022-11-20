package ink.bluecloud.service

import ink.bluecloud.model.networkapi.api.NetWorkResourcesProvider
import ink.bluecloud.network.http.HttpClient
import ink.bluecloud.utils.settingloader.SettingCenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class ClientService: KoinComponent {
    protected val logger: Logger by lazy {
        LoggerFactory.getLogger(javaClass)
    }

    //协程控制资源
    private val ioScope by inject<CoroutineScope>(named("ioScope"))
    private val uiScope by inject<CoroutineScope>(named("uiScope"))

    protected val netWorkResourcesProvider by inject<NetWorkResourcesProvider>()

    protected val httpClient by inject<HttpClient>()

    protected val settingCenter by inject<SettingCenter>()


    /**
    * 运行在JavaFx协程上
    * */
    protected fun newUI(block: suspend CoroutineScope.() -> Unit) {
        uiScope.launch {
            block()
        }
    }

    protected suspend fun <T> ui(block: suspend CoroutineScope.() -> T):T = withContext(uiScope.coroutineContext) {
        block()
    }

    /**
     * 运行在IO协程上
     * */
    protected fun newIO(block: suspend CoroutineScope.() -> Unit) {
        ioScope.launch {
            block()
        }
    }

    protected suspend fun <T> io(block: suspend CoroutineScope.() -> T):T = withContext(ioScope.coroutineContext) {
        block()
    }
}