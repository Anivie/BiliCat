package ink.bluecloud.ui.mainview

import ink.bluecloud.cloudtools.stageinitializer.TitleBar
import ink.bluecloud.service.clientservice.init.LoadCookie
import ink.bluecloud.service.clientservice.video.hot.VideoWeeklyList
import ink.bluecloud.service.clientservice.video.stream.VideoStream
import ink.bluecloud.service.clientservice.video.stream.param.Qn
import ink.bluecloud.ui.HarmonySans
import ink.bluecloud.ui.fragment.VideoPlayer
import ink.bluecloud.ui.mainview.homeview.HomeView
import ink.bluecloud.ui.mainview.node.sliderbar.CloudSlideBar
import ink.bluecloud.utils.ioScope
import ink.bluecloud.utils.uiContext
import javafx.geometry.Pos
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import tornadofx.*

class MainView: KoinComponent,MainViewNodes() {

    override val root = borderpane root@{
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
                        ioScope.launch {
                            get<LoadCookie>().load()
                            val (_, _, _, id, cid, _, _, _, _, _) = get<VideoWeeklyList>().getVideos().first()
                            get<VideoStream>().getVideoStream(id, cid).run {
                                withContext(uiContext) {
                                    find<VideoPlayer>(params = mapOf("url" to video.get(Qn.P720_ALL.value).values().first()[0])).openWindow()
                                }
                            }
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


        right {
            rightBox = borderpane {
                minWidth = 300.0//todo
                paddingVertical = 40
                paddingHorizontal = 20
            }
        }

        style {
            backgroundColor += Color.WHITE
        }

        prefWidth = 1200.0
        prefHeight = 900.0
    }

    override fun onDock() {
        get<MainViewController>().run {
            initUi()
        }
    }
}