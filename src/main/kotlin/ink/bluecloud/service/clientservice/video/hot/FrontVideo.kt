package ink.bluecloud.service.clientservice.video.hot

import ink.bluecloud.model.data.video.HomePagePushCard
import ink.bluecloud.service.clientservice.APIResources
import kotlinx.coroutines.flow.Flow

abstract class FrontVideo: APIResources() {
    abstract suspend fun getVideos(): Flow<HomePagePushCard>
}