package ink.bluecloud.service.video.hot

import ink.bluecloud.model.data.video.HomePagePushCard
import ink.bluecloud.service.APIResources
import kotlinx.coroutines.flow.Flow

abstract class FrontVideo: APIResources() {
    abstract suspend fun getVideos(): Flow<HomePagePushCard>
}