package ink.bluecloud.service

import ink.bluecloud.model.networkapi.api.NetWorkResourcesProvider
import ink.bluecloud.network.http.HttpClient
import ink.bluecloud.utils.settingloader.SettingCenter
import kotlinx.coroutines.CoroutineScope
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
    protected val ioScope by inject<CoroutineScope>(named("ioScope"))
    protected val uiScope by inject<CoroutineScope>(named("uiScope"))

    protected val netWorkResourcesProvider by inject<NetWorkResourcesProvider>()

    protected val httpClient by inject<HttpClient>()

    protected val settingCenter by inject<SettingCenter>()


    protected suspend fun <T> ui(block: suspend CoroutineScope.() -> T):T = withContext(uiScope.coroutineContext) {
        block()
    }

    protected suspend fun <T> io(block: suspend CoroutineScope.() -> T):T = withContext(ioScope.coroutineContext) {
        block()
    }
}