package ink.bluecloud.ui.mainview.homeview

import ink.bluecloud.service.video.hot.FrontVideo
import ink.bluecloud.service.video.hot.FullRank
import ink.bluecloud.service.video.hot.HotVideoList
import ink.bluecloud.service.video.hot.VideoWeeklyList
import ink.bluecloud.ui.CloudController
import ink.bluecloud.ui.fragment.videoview.VideoView
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
import org.koin.core.parameter.parametersOf
import tornadofx.hgrow
import tornadofx.vgrow

@Single
class HomeViewController: CloudController<HomeView>() {
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
            onUI {
                rootBox.sceneRoot.children += get<VideoView> {
                    parametersOf(
                        videoInformationCard.currentCard
                    )
                }
            }
        }

        return videoInformationCard
    }
}