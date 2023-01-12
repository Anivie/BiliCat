package ink.bluecloud.ui.mainview.node.sliderbar

import ink.bluecloud.cloudtools.CLOUD_INTERPOLATOR
import ink.bluecloud.utils.HarmonySans
import javafx.animation.Animation.Status.*
import javafx.animation.TranslateTransition
import javafx.beans.property.SimpleIntegerProperty
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Cursor
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.util.Duration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import tornadofx.*

class CloudSlideBar(items: Map<String, String>, onChange: (Pair<Int, Int>) -> Unit): StackPane() {
    private val selectIndex = SimpleIntegerProperty()
//    private val icon = Font.loadFont(Thread.currentThread().contextClassLoader.getResourceAsStream("css/icon_home.ttf"), 12.0)

    private var disableSlider = false

    init {
        var baseBox:Pane? = null
        borderpane {
            top {
                baseBox = vbox(5) {
                    val myClickHandler = MyClickHandler(disableSlider, selectIndex)
                    val myEnterHandler = MyEnterHandler(selectIndex)
                    val myLeaveHandler = MyLeaveHandler(selectIndex)
                    val iterator = items.iterator()
                    repeat(items.size) {
                        children += getItem(iterator.next(),it, myClickHandler,myEnterHandler, myLeaveHandler)
                    }
                }
            }

            padding = insets(0, 20, 0, 0)
        }

        val region = region {
            maxWidth = 2.5
            maxHeightProperty().bind((baseBox!!.children[0] as HBox).heightProperty())

            style {
                backgroundColor += c(24, 144, 255)
            }
        }

        @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
        val transition = TranslateTransition(Duration.millis(250.0), region).apply {
            interpolator = CLOUD_INTERPOLATOR
            statusProperty().addListener { _, _, newValue ->
                disableSlider = when (newValue) {
                    RUNNING -> {
                        true
                    }
                    STOPPED -> {
                        false
                    }
                    PAUSED -> TODO()
                }
            }
        }

        val coroutineScope = CoroutineScope(Dispatchers.JavaFx)
        selectIndex.addListener { _, oldValue, newValue ->
            coroutineScope.launch {
                launch {
                    onChange(Pair(oldValue.toInt(),newValue.toInt()))
                }

                launch {
                    (baseBox!!.children[oldValue.toInt()]as HBox).run {
                        children[0].run {
                            style(true) {
                                textFill = c(95,95,95,0.5)
                            }
                        }

                        children[1].run {
                            style(true) {
                                textFill = c(95,95,95,0.5)
                            }
                        }
                    }

                    (baseBox!!.children[newValue.toInt()]as HBox).run {
                        children[0].run {
                            style(true) {
                                textFill = c(24,144,255)
                            }
                        }
                        children[1].run {
                            style(true) {
                                textFill = c(95,95,95)

                            }
                        }
                    }

                    transition.apply {
                        toY = (baseBox!!.children[newValue.toInt()]as HBox).layoutY
                    }.play()
                }
            }
        }

        stylesheets += "ui/homeview/font/icon_home.css"
        alignment = Pos.TOP_RIGHT
    }

    private fun getItem(title: Map.Entry<String, String>, index: Int, myClickHandler: MyClickHandler, myEnterHandler: MyEnterHandler, myLeaveHandler: MyLeaveHandler):HBox {
        return HBox(20.0).apply {
            label(title.key) {
                style {
                    textFill = if (index == 0) {
                        c(24,144,255)
                    } else {
                        c(95,95,95, 0.5)
                    }

                    fontSize = 20.px
                    fontFamily = "bilibilifx-home"
                }
            }

            label(title.value) {
                style {
                    textFill = if (index == 0) {
                        c(95,95,95)
                    } else {
                        c(95,95,95, 0.5)
                    }

                    fontSize = 15.px
                    fontFamily = HarmonySans.BOLD
                }
            }

            style {
                cursor = Cursor.HAND
                padding = box(10.px,20.px,10.px,0.px)
            }

            heightProperty().addListener { _, _, newValue ->
                style(true) {
                    backgroundRadius += box((newValue.toDouble() / 2).px)
                }
            }

            onMouseClicked = myClickHandler
            onMouseEntered = myEnterHandler
            onMouseExited = myLeaveHandler

            userData = index
            alignment = Pos.CENTER
        }
    }

    private class MyClickHandler(private val disableSlider: Boolean, private val selectIndex: SimpleIntegerProperty): EventHandler<MouseEvent> {
        override fun handle(event: MouseEvent) {
            if (!disableSlider) selectIndex.value = (event.source as Pane).userData as Int
        }
    }

    private class MyEnterHandler(private val selectIndex: SimpleIntegerProperty): EventHandler<MouseEvent> {
        override fun handle(event: MouseEvent) {
            val origin = event.source as HBox
            val index = origin.userData as Int
            if (selectIndex.value == index) return

            origin.children[1].style(true) {
                textFill = c(95,95,95, 0.75)
            }
        }
    }

    private class MyLeaveHandler(private val selectIndex: SimpleIntegerProperty): EventHandler<MouseEvent> {
        override fun handle(event: MouseEvent) {
            val origin = event.source as HBox
            val index = origin.userData as Int
            if (selectIndex.value == index) return

            origin.children[1].style(true) {
                textFill = c(95,95,95, 0.5)
            }
        }
    }

}