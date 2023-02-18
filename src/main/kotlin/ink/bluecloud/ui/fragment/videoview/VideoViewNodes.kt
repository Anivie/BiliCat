package ink.bluecloud.ui.fragment.videoview

import ink.bluecloud.service.video.stream.VideoStream
import javafx.scene.layout.VBox
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class VideoViewNodes: KoinComponent,VBox() {
    protected val videoStream by inject<VideoStream>()
}