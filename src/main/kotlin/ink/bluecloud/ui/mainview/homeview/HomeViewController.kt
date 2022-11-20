package ink.bluecloud.ui.mainview.homeview

import ink.bluecloud.service.ClientService
import ink.bluecloud.service.clientservice.video.hot.HotVideoList
import ink.bluecloud.service.clientservice.video.hot.VideoWeeklyList
import ink.bluecloud.service.clientservice.video.rank.FullRank
import ink.bluecloud.ui.Controller
import ink.bluecloud.ui.mainview.homeview.node.ShowWindow
import javafx.scene.layout.Priority
import org.koin.core.annotation.Single
import org.koin.core.component.get
import org.koin.core.component.getScopeId
import org.koin.core.component.getScopeName
import org.koin.core.parameter.parametersOf
import tornadofx.*

@Single
class HomeViewController: Controller() {

    override fun View.initUi() {
        this as HomeView
/*        dispatcher.service<VideoServiceProvider, HotVideoList> {
            getVideos {
                rootBox.children += find<ShowWindow>(params = mapOf("images" to it)).root.apply {
                    vgrow = Priority.SOMETIMES
                }
            }
        }*/

        ui {
            val videos = get<HotVideoList>().getVideos()
            rootBox.children += get<ShowWindow> {
                parametersOf(videos)
            }.apply {
                vgrow = Priority.SOMETIMES
            }
        }

        ui {
            val videos = get<VideoWeeklyList>().getVideos()
            secondBox.children += get<ShowWindow> {
                parametersOf(videos, secondBox.widthProperty(), secondBox.spacing)
            }.apply {
                hgrow = Priority.SOMETIMES
            }
        }

/*
        dispatcher.service<VideoServiceProvider, VideoWeeklyList> {
            getVideos {
                secondBox.children += find<ShowWindow>(
                    params = mapOf(
                        "images" to it,
                        "width" to secondBox.widthProperty(),
                        "spacing" to secondBox.spacing
                    )
                ).root.apply {
                    hgrow = Priority.SOMETIMES
                }
            }
        }
*/

/*
        dispatcher.service<VideoServiceProvider, FullRank> {
            getVideos {
                secondBox.children += find<ShowWindow>(params = mapOf(
                    "images" to it,
                    "width" to secondBox.widthProperty(),
                    "spacing" to secondBox.spacing
                )).root.apply {
                    hgrow = Priority.SOMETIMES
                }
            }
        }
*/

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