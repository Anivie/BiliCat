package ink.bluecloud.ui.mainview.homeview.node

import ink.bluecloud.cloudtools.CLOUD_INTERPOLATOR
import ink.bluecloud.model.data.video.HomePagePushCard
import ink.bluecloud.utils.ioScope
import ink.bluecloud.utils.uiContext
import ink.bluecloud.utils.uiScope
import ink.bluecloud.utils.uiutil.cloudTimeline
import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.beans.property.SimpleObjectProperty
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import org.koin.core.component.KoinComponent
import tornadofx.*

@Factory
class VideoInformationCard(
//    private val name: String,
    private val card: Flow<HomePagePushCard>,
    private val widthProperty: ReadOnlyDoubleProperty?,
    private val spacing: Double?
): KoinComponent,StackPane() {

    private val currentCardProperty:SimpleObjectProperty<HomePagePushCard>
    var currentCard: HomePagePushCard by SimpleObjectProperty<HomePagePushCard>().apply {
        currentCardProperty = this
    }

    init {
        uiScope.launch {
            pane {
                currentCardProperty.addListener { _, _, newValue ->
                    ioScope.launch {
                        val stream = newValue.cover.await()
                        withContext(uiContext) {
                            background = Background(
                                BackgroundImage(
                                    Image(stream),
                                    BackgroundRepeat.NO_REPEAT,
                                    BackgroundRepeat.NO_REPEAT,
                                    BackgroundPosition.CENTER,
                                    BackgroundSize(1.0,1.0,true,true,true,true)
                                )
                            )
                        }
                    }
                }
            }

            stackpane {
                label {
                    style {
                        textFill = Color.WHITE
                    }

                    textProperty().bind(currentCardProperty.map { it.title })

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

            currentCard = card.first()
//            card.collect {
//                println("running on ${Thread.currentThread().name} get ${it}")
//            }

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
                widthProperty().bind(this@VideoInformationCard.widthProperty())
                heightProperty().bind(this@VideoInformationCard.heightProperty())

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