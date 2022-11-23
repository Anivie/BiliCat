package ink.bluecloud.ui.mainview.homeview

import ink.bluecloud.service.clientservice.video.hot.HotVideoList
import ink.bluecloud.service.clientservice.video.hot.VideoWeeklyList
import ink.bluecloud.service.clientservice.video.rank.FullRank
import ink.bluecloud.ui.Controller
import ink.bluecloud.ui.fragment.javafxmediaplayer.VideoPlayer
import ink.bluecloud.ui.mainview.homeview.node.ShowWindow
import ink.bluecloud.utils.sceneRoot
import javafx.scene.layout.Priority
import org.koin.core.annotation.Single
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import tornadofx.*

@Single
class HomeViewController: Controller<HomeView>() {
    override fun initUi(view: HomeView) = view.run {
        ui {
            val videos = get<HotVideoList>().getVideos()
            val showWindow = get<ShowWindow> {
                parametersOf(videos)
            }.apply {
                vgrow = Priority.SOMETIMES
            }

            showWindow.setOnMouseClicked {
                rootBox.sceneRoot.children += get<VideoPlayer> {
                    parametersOf(
                        showWindow
                    )
                }
            }

            rootBox.children += showWindow
        }

        ui {
            val videos = get<VideoWeeklyList>().getVideos()
            secondBox.children += get<ShowWindow> {
                parametersOf(videos, secondBox.widthProperty(), secondBox.spacing)
            }.apply {
                hgrow = Priority.SOMETIMES
            }
        }

        ui {
            val videos = get<FullRank>().getVideos()
            secondBox.children += get<ShowWindow> {
                parametersOf(videos, secondBox.widthProperty(), secondBox.spacing)
            }.apply {
                hgrow = Priority.SOMETIMES
            }
        }
    }
}