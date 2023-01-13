package ink.bluecloud.model.networkapi.api

import ink.bluecloud.model.networkapi.api.data.HttpApi
import org.koin.core.component.KoinComponent

abstract class NetWorkResourcesProvider:KoinComponent {
    abstract val api: HttpApi
}