package ink.bluecloud.ui.mainview

import ink.bluecloud.cloudtools.stageinitializer.TitleBar
import ink.bluecloud.ui.mainview.homeview.HomeView
import ink.bluecloud.ui.mainview.node.ExperienceBar
import ink.bluecloud.ui.mainview.node.SuspensionProFileCard
import ink.bluecloud.ui.mainview.node.sliderbar.CloudSlideBar
import ink.bluecloud.utils.HarmonySans
import ink.bluecloud.utils.koin
import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Pos
import javafx.scene.Cursor
import javafx.scene.effect.BlurType
import javafx.scene.effect.DropShadow
import javafx.scene.input.MouseEvent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.stage.WindowEvent
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import tornadofx.*

class MainView : KoinComponent,MainViewNodes() {
    override fun onDock() {
        get<MainViewController>().initUi(this)
        koin.setProperty("primaryStage", primaryStage)
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

                top = hbox(10,Pos.CENTER) profileCard@{
                    headView = imageview("ui/homeview/logo.png") view@{
                        clip = Circle(25.0, 25.0,25.0)

                        fitWidth = 50.0
                        fitHeight = 50.0
                    }

                    vbox(3) {
                        userName = label("BiliCat") {
                            style {
                                textFill = c("gray")
                                fontSize = 25.px
                                fontFamily = HarmonySans.BOLD
                            }
                        }

                        vbox(1) showBox@{
                            label("Lv: 6")
                            levelBar = ExperienceBar(6).apply {
                                this@showBox.children += this
                            }
                        }
                    }

                    effect = DropShadow(BlurType.GAUSSIAN, c(0,0,0,0.1), 20.0,0.0,7.0,10.0)

                    val open = SimpleBooleanProperty()
                    addEventHandler(MouseEvent.MOUSE_MOVED) {
                        open.value = ((it.x in 0.0.. 190.0) && (it.y in 0.0..100.0)) || ((it.x in 0.0..190.0) && (it.y >= 0))
                    }

                    var suspensionProFileCard: SuspensionProFileCard? = null
                    primaryStage.addEventHandler(WindowEvent.WINDOW_SHOWN) {
                        suspensionProFileCard = get {
                            parametersOf(this@profileCard, open)
                        }
                    }

                    open.addListener { _, _, newValue ->
                        if (!newValue) return@addListener
                        val rootChildren = (scene.root as Pane).children

                        if (!rootChildren.contains(suspensionProFileCard)) rootChildren += suspensionProFileCard
                    }

                    style {
                        backgroundColor += Color.WHITE
                        backgroundRadius += box(20.px)
                    }

                    paddingAll = 20
                    cursor = Cursor.HAND
                    BorderPane.setMargin(this, insets(0, 20, 0, 0))
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