package ink.bluecloud.ui.mainview.homeview

import ink.bluecloud.ui.HarmonySans
import javafx.geometry.NodeOrientation
import javafx.geometry.Pos
import javafx.scene.control.TabPane
import javafx.scene.control.skin.TabPaneSkin
import javafx.scene.layout.Pane
import javafx.scene.layout.Priority
import javafx.scene.layout.StackPane
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import tornadofx.*

class HomeView: KoinComponent,HomeViewNodes() {

    override fun onDock() {
        get<HomeViewController>().initUi(this)
    }

    override val root = vbox(20) {
        borderpane base@{
            left = stackpane {
                textfield {
                    promptText = "在这里开始搜索"

                    style {
                        padding = box(10.px, 10.px, 10.px, 25.px)
                    }

                    prefWidthProperty().bind(this@base.widthProperty().divide(2))
                }

                label("\uEAFE") {
                    style {
                        fontFamily = "bilibilifx-home"
                        fontSize = 17.px
                    }
                    StackPane.setMargin(this, insets(0, 0, 0, 5))
                }

                alignment = Pos.CENTER_LEFT
            }

            right = button("\uE716") {
                stylesheets += "css/node/suspended-button.css"
            }
            fitToParentWidth()
        }

        stackpane {
            tabpane {
                tab("个人榜单") {

                }
                tab("分区榜单") {

                }
                tab("全站榜单") {
                    rootBox = vbox(20) {

                        secondBox = hbox(20) {
                            vgrow = Priority.SOMETIMES
                        }
                    }
                }

                skinProperty().addListener { _, _, newValue ->
                    newValue as TabPaneSkin

                    (newValue.javaClass.getDeclaredField("tabHeaderArea").apply {
                        isAccessible = true
                    }.get(newValue)as StackPane).run {
                        nodeOrientation = NodeOrientation.RIGHT_TO_LEFT
                    }

                    newValue.children.forEach {
                        if (it.styleClass[0] != "tab-content-area") return@addListener
                        it as Pane

                        it.padding = insets(20,30)
                    }
                }

                selectionModel.select(2)
                stylesheets += "css/node/home-tabpane.css"
                tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
            }

            label("今日榜单") {
                style {
                    fontSize = 20.px
                    textFill = c("black")
                    fontFamily = HarmonySans.BOLD
                }

                paddingTop = 10
            }

            alignment = Pos.TOP_LEFT
            fitToParentHeight()
        }

        stylesheets += "ui/homeview/font/icon_home.css"
    }
}