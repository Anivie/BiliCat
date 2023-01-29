package ink.bluecloud.ui.mainview.homeview

import ink.bluecloud.service.video.hot.FrontVideo
import ink.bluecloud.service.video.hot.FullRank
import ink.bluecloud.service.video.hot.HotVideoList
import ink.bluecloud.service.video.hot.VideoWeeklyList
import ink.bluecloud.service.video.stream.VideoStream
import ink.bluecloud.ui.CloudController
import ink.bluecloud.ui.fragment.javafxmediaplayer.PlayingData
import ink.bluecloud.ui.fragment.javafxmediaplayer.VideoPlayer
import ink.bluecloud.ui.mainview.homeview.node.VideoInformationCard
import ink.bluecloud.utils.newIO
import ink.bluecloud.utils.newUI
import ink.bluecloud.utils.onUI
import ink.bluecloud.utils.sceneRoot
import ink.bluecloud.utils.uiutil.newSuspendEventHandler
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Priority
import org.koin.core.annotation.Single
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import tornadofx.hgrow
import tornadofx.vgrow

@Single
class HomeViewController: CloudController<HomeView>() {
    private val videoStream by inject<VideoStream>()
    override fun   initUi(view: HomeView) = view.run {
        newIO {
            val videoInformationCard = generateVideoInformationCard<HotVideoList>()

            onUI {
                rootBox.children += videoInformationCard
            }
        }

        newIO {
            val videoInformationCard = generateVideoInformationCard<VideoWeeklyList>()

            onUI {
                secondBox.children += videoInformationCard
            }
        }

        //Bug if using io block,can't fix.
        newUI {
            val videoInformationCard = generateVideoInformationCard<FullRank>()

            secondBox.children += videoInformationCard
        }
    }

    private suspend inline fun <reified T: FrontVideo> HomeView.generateVideoInformationCard(): VideoInformationCard {
        val videos = get<T>().getVideos()
        val videoInformationCard = get<VideoInformationCard> {
            when (T::class.simpleName) {
                "HotVideoList" -> parametersOf(videos)
                else -> parametersOf(videos, secondBox.widthProperty(), secondBox.spacing)
            }
        }.apply {
            vgrow = Priority.SOMETIMES
            hgrow = Priority.ALWAYS
        }

        videoInformationCard.newSuspendEventHandler(MouseEvent.MOUSE_CLICKED) {
            val (video, audio) = videoStream.getVideoStream(
                videoInformationCard.currentCard.id,
                videoInformationCard.currentCard.cid
            )

            onUI {
                rootBox.sceneRoot.children += get<VideoPlayer> {
                    parametersOf(
                        PlayingData(
                            video.get().url.first(),
                            audio.get().url.first()
                        )
                    )
                }
            }
        }

        return videoInformationCard
    }
}