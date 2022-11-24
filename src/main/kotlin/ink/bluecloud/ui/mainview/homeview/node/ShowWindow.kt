package ink.bluecloud.ui.mainview.homeview.node

import ink.bluecloud.cloudtools.CLOUD_INTERPOLATOR
import ink.bluecloud.model.data.video.HomePagePushCard
import ink.bluecloud.ui.cloudTimeline
import ink.bluecloud.utils.uiScope
import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.geometry.Pos
import javafx.scene.Cursor
import javafx.scene.image.Image
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop
import javafx.scene.shape.Rectangle
import javafx.util.Duration
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory
import org.koin.core.component.KoinComponent
import tornadofx.*

@Factory
class ShowWindow(
    private val card: Flow<HomePagePushCard>,
    private val widthProperty: ReadOnlyDoubleProperty?,
    private val spacing: Double?
): KoinComponent,StackPane() {

//    lateinit var currentUrl:String

    init {
        uiScope.launch {
            val image = card.first()
            val cover = image.cover.await()

            pane {
                background = Background(BackgroundImage(
                    Image(cover),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    BackgroundSize(1.0,1.0,true,true,true,true)
                ))
            }

            stackpane {
                label(image.title) {
                    style {
                        textFill = Color.WHITE
                    }

                    paddingBottom = 5
                    paddingLeft = 5
                }

                background = Background(BackgroundFill(
                    LinearGradient(
                        1.0, 0.0, 1.0, 1.0, true, CycleMethod.NO_CYCLE,
                        Stop(0.0, Color(0.0, 0.0, 0.0, 0.0)),
                        Stop(1.0, Color(0.0, 0.0, 0.0, 1.0))
                    ),
                    CornerRadii.EMPTY,
                    null
                ))

                prefHeight = 30.0
                maxHeight = 30.0
                alignment = Pos.BOTTOM_LEFT
                setAlignment(this,Pos.BOTTOM_LEFT)
            }



            val zoomAnimation = cloudTimeline {
                keyframe(Duration.millis(300.0)) {
                    keyvalue(scaleXProperty(), 1.05, CLOUD_INTERPOLATOR)
                    keyvalue(scaleYProperty(), 1.05, CLOUD_INTERPOLATOR)
                }
            }
            setOnMouseEntered {
                zoomAnimation.play()
            }

            val outAnimation = cloudTimeline {
                keyframe(Duration.millis(300.0)) {
                    keyvalue(scaleXProperty(), 1.0, CLOUD_INTERPOLATOR)
                    keyvalue(scaleYProperty(), 1.0, CLOUD_INTERPOLATOR)
                }
            }
            setOnMouseExited {
                outAnimation.play()
            }

            clip = Rectangle().apply {
                widthProperty().bind(this@ShowWindow.widthProperty())
                heightProperty().bind(this@ShowWindow.heightProperty())

                arcWidth = 20.0
                arcHeight = 20.0
            }

            widthProperty?.run {
                spacing?.run {
                    if (value != 0.0) maxWidth = (value - spacing) / 2

                    widthProperty.addListener { _, _, newValue ->
                        maxWidth = (newValue.toDouble() - spacing) / 2
                    }
                }
            }

            cursor = Cursor.HAND
        }
    }
}