package ink.bluecloud.ui.mainview

import ink.bluecloud.cloudtools.stageinitializer.TitleBar
import ink.bluecloud.cloudtools.stageinitializer.initCustomizeStage
import ink.bluecloud.service.clientservice.video.hot.VideoWeeklyList
import ink.bluecloud.service.clientservice.video.stream.VideoStream
import ink.bluecloud.service.clientservice.video.stream.param.Qn
import ink.bluecloud.ui.HarmonySans
import ink.bluecloud.ui.fragment.VideoPlayer
import ink.bluecloud.ui.mainview.homeview.HomeView
import ink.bluecloud.ui.mainview.node.sliderbar.CloudSlideBar
import ink.bluecloud.utils.uiScope
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.stage.StageStyle
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import tornadofx.*

class MainView : KoinComponent,MainViewNodes() {
    override fun onDock() {
        get<MainViewController>().initUi(this)
    }

    override val root = stackpane {
        borderpane root@{
            top = TitleBar("BilibiliFX", primaryStage).apply {
                paddingBottom = 10
            }

            center = stackpane {
                style {
                    backgroundColor += c(0,90,179,0.03)
                }

                paddingHorizontal = 50
                paddingVertical = 50
            }

            left = borderpane leftBox@{
                top = vbox(20,Pos.CENTER) {
                    hbox(10,Pos.CENTER) {
                        imageview("ui/homeview/logo.png") {
                            fitWidth = 50.0
                            fitHeight = 50.0
                        }

                        label("BilibiliFX") {
                            style {
                                fontSize = 25.px
                                fontFamily = HarmonySans.BOLD
                            }
                        }
                    }

                    button("Debug!") {
                        action {
                            uiScope.launch {
                                val (_, _, _, id, cid, _, _, _, _, _) = get<VideoWeeklyList>().getVideos().filter {
                                    it.title == "我用400天，做了一款让所有人免费商用的开源字体"
                                }.first()

                                val (video, _) = get<VideoStream>().getVideoStream(id, cid)

                                Stage(StageStyle.TRANSPARENT).apply {
                                    scene = Scene(get<VideoPlayer> {
                                        parametersOf(
                                            video.getVideoStreamData(Qn.P306_ALL).url[0]
//                                            video.getVideoStreamData(Qn.P1080_ALL_COOKIE).keepURL()
                                        )
                                    })

                                    width = 1000.0
                                    height = 1000.0
                                    initCustomizeStage()
                                }.show()
                            }
                        }

                        style {
                            padding = box(10.px,50.px)
                        }
                    }
                    paddingRight = 20
                }

                center = CloudSlideBar(mapOf(
                    "\uE63E" to "主页",
                    "\uE6B6" to "推荐",
                    "\uE610" to "动态",
                    "\uE629" to "分区"
                )) {
/*
                (this@root.center as StackPane).children[0] = when (it.second) {
                    0 -> find<HomeView>().root
                    else -> throw IllegalArgumentException("无法解析的页面：${it}！")
                }
*/
                }.apply {
                    (this@root.center as StackPane).children += find<HomeView>().root
                    maxWidthProperty().bind(this@leftBox.widthProperty())
                    BorderPane.setMargin(this, insets(0.0, 40.0))
                }

                padding = insets(20.0, 0.0, 20.0, 20.0)
            }


            /*
                    right {
                        rightBox = borderpane {
                            minWidth = 300.0//todo
                            paddingVertical = 40
                            paddingHorizontal = 20
                        }
                    }
            */

            style {
                backgroundColor += Color.WHITE
            }

            prefWidth = 1200.0
            prefHeight = 900.0
        }
    }
}