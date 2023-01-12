package ink.bluecloud.service.clientservice.video.hot

import ink.bluecloud.model.data.video.HomePagePushCard
import ink.bluecloud.service.ClientService
import kotlinx.coroutines.flow.Flow

abstract class FrontVideo: ClientService() {
    abstract suspend fun getVideos(): Flow<HomePagePushCard>
}