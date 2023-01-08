package ink.bluecloud.ui.mainview.homeview

import ink.bluecloud.service.clientservice.video.hot.HotVideoList
import ink.bluecloud.service.clientservice.video.hot.VideoWeeklyList
import ink.bluecloud.service.clientservice.video.rank.FullRank
import ink.bluecloud.service.clientservice.video.stream.VideoStream
import ink.bluecloud.ui.CloudController
import ink.bluecloud.ui.fragment.javafxmediaplayer.PlayingData
import ink.bluecloud.ui.fragment.javafxmediaplayer.VideoPlayer
import ink.bluecloud.ui.mainview.homeview.node.ShowWindow
import ink.bluecloud.utils.sceneRoot
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
    override fun initUi(view: HomeView) = view.run {
        ui {
            val videos = get<HotVideoList>().getVideos()
            val showWindow = get<ShowWindow> {
                parametersOf(videos)
            }.apply {
                vgrow = Priority.SOMETIMES
            }

            showWindow.setOnMouseClicked {
                ui {
                    val (video, audio) = videoStream.getVideoStream(showWindow.currentCard.id, showWindow.currentCard.cid)

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

            rootBox.children += showWindow
        }

        ui {
            val videos = get<VideoWeeklyList>().getVideos()
            val showWindow = get<ShowWindow> {
                parametersOf(videos, secondBox.widthProperty(), secondBox.spacing)
            }.apply {
                hgrow = Priority.SOMETIMES
            }

            showWindow.setOnMouseClicked {
                ui {
                    val (video, audio) = videoStream.getVideoStream(showWindow.currentCard.id, showWindow.currentCard.cid)

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

            secondBox.children += showWindow
        }

        ui {
            val videos = get<FullRank>().getVideos()
            val showWindow = get<ShowWindow> {
                parametersOf(videos, secondBox.widthProperty(), secondBox.spacing)
            }.apply {
                hgrow = Priority.SOMETIMES
            }

            showWindow.setOnMouseClicked {
                ui {
                    val (video, audio) = videoStream.getVideoStream(showWindow.currentCard.id, showWindow.currentCard.cid)

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

            secondBox.children += showWindow
        }
    }
}