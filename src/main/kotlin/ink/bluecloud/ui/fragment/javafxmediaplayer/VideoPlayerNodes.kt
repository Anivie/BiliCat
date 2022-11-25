package ink.bluecloud.ui.fragment.javafxmediaplayer

import ink.bluecloud.service.clientservice.video.player.VideoPlayerBuilder
import ink.bluecloud.ui.fragment.javafxmediaplayer.node.ControlBar
import ink.bluecloud.utils.getValue
import ink.bluecloud.utils.setValue
import javafx.beans.property.SimpleDoubleProperty
import javafx.scene.layout.StackPane
import kotlinx.coroutines.Job
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tornadofx.*
import java.util.concurrent.atomic.AtomicReference


abstract class VideoPlayerNodes: KoinComponent,StackPane() {
    val builder by inject<VideoPlayerBuilder>()
    val timer = SimpleDoubleProperty(5.0)

    var controlBar by singleAssign<ControlBar>()
    var job by AtomicReference<Job?>()
}