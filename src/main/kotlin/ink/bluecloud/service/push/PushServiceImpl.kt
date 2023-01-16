package ink.bluecloud.service.push

import ink.bluecloud.cloudtools.cloudnotice.CloudNotice
import ink.bluecloud.cloudtools.cloudnotice.Property.NoticeType
import ink.bluecloud.utils.ioScope
import ink.bluecloud.utils.uiContext
import javafx.stage.Window
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import tornadofx.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Single
class PushServiceImpl: PushService() {
    private val noticeChannel = Channel<CloudNotice>().apply channel@{
        ioScope.launch {
            while (isActive) {
                val notice = this@channel.receive()

                notice.showNotice()

                suspendCoroutine {
                    notice.popup.setOnHiding { _ ->
                        it.resume(Unit)
                    }
                }
            }
        }
    }

    private suspend fun CloudNotice.showNotice() = withContext(uiContext) {
        show()
    }

    override suspend fun makeNotice(type: NoticeType, message: String, window: Window) = withContext(uiContext) {
        noticeChannel.send(CloudNotice(type,message,window))
    }
}

context(UIComponent)
suspend inline fun PushService.makeNotice(type: NoticeType, message: String) {
    makeNotice(type,message,primaryStage)
}