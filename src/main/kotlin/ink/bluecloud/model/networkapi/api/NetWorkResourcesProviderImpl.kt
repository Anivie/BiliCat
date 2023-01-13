package ink.bluecloud.model.networkapi.api

import ink.bluecloud.model.networkapi.api.data.HttpApi
import ink.bluecloud.service.seeting.SettingCenter
import ink.bluecloud.service.seeting.loadSetting
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.core.annotation.Single
import org.koin.core.component.get
import org.koin.core.error.NoPropertyFileFoundException

@Single
@OptIn(ExperimentalSerializationApi::class)
class NetWorkResourcesProviderImpl: NetWorkResourcesProvider() {
    override val api: HttpApi = get<SettingCenter>().loadSetting()?: throw NoPropertyFileFoundException("No HttpApi found!Check your config and try again!")
}