package ink.bluecloud.service.clientservice.release

import ink.bluecloud.service.ClientService
import org.koin.core.annotation.Factory


@Factory
class ReleaseService: ClientService() {
    fun onExit() {
        httpClient.okHttpClient.dispatcher.executorService.shutdown()
    }
}