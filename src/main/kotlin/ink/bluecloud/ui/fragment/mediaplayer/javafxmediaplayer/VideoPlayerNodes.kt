package ink.bluecloud.ui.fragment.mediaplayer.javafxmediaplayer

import ink.bluecloud.service.video.player.VideoPlayerBuilder
import ink.bluecloud.ui.fragment.mediaplayer.javafxmediaplayer.node.ControlBar
import ink.bluecloud.utils.getValue
import ink.bluecloud.utils.setValue
import javafx.beans.property.SimpleObjectProperty
import javafx.event.EventTarget
import javafx.scene.layout.StackPane
import kotlinx.coroutines.Job
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import tornadofx.*
import java.util.concurrent.atomic.AtomicReference
import kotlin.reflect.KClass


abstract class VideoPlayerNodes: KoinComponent,StackPane() {
    val builder by inject<VideoPlayerBuilder>()

    var controlBar by singleAssign<ControlBar>()

    val timerValue = 3000L

    var timer: Job? by AtomicReference()
    var timerTarget = SimpleObjectProperty<KClass<out EventTarget>>()
}