package ink.bluecloud.model.networkapi.api

import ink.bluecloud.model.networkapi.api.data.HttpApi
import java.nio.file.Paths

abstract class NetWorkResourcesProvider {
    protected val apiFilePath = Paths.get("config\\HttpAPI.proto")
    abstract val api: HttpApi
}