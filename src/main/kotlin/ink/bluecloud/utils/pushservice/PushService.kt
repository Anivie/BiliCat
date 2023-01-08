package ink.bluecloud.utils.pushservice

import ink.bluecloud.cloudtools.cloudnotice.Property.NoticeType
import javafx.stage.Window
import org.koin.core.component.KoinComponent

abstract class PushService: KoinComponent {
    abstract suspend fun makeNotice(type: NoticeType, message: String, window: Window)
}