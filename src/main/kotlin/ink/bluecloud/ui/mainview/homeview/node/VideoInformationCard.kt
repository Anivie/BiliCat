package ink.bluecloud.ui.mainview.homeview.node

import ink.bluecloud.cloudtools.CLOUD_INTERPOLATOR
import ink.bluecloud.model.data.video.HomePagePushCard
import ink.bluecloud.utils.newIO
import ink.bluecloud.utils.onUI
import ink.bluecloud.utils.uiutil.*
import javafx.beans.binding.Bindings
import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Pos
import javafx.scene.Cursor
import javafx.scene.image.Image
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop
import javafx.scene.shape.Rectangle
import javafx.util.Duration
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory
import org.koin.core.component.KoinComponent
import tornadofx.*

@Factory([VideoInformationCard::class])
class VideoInformationCard(
    private val card: Flow<HomePagePushCard>,
    private val widthProperty: ReadOnlyDoubleProperty?,
    private val spacing: Double?
): KoinComponent,StackPane() {

    private val currentCardProperty:SimpleObjectProperty<HomePagePushCard>
    var currentCard: HomePagePushCard by SimpleObjectProperty<HomePagePushCard>().apply {
        currentCardProperty = this
    }

    private val cardCache = ArrayList<HomePagePushCard>()
    private val coverCache = HashMap<HomePagePushCard, Image>()
    private var cacheIndex = 0

    init {
        pane {
            currentCardProperty.newSuspendEventHandler {
                val stream = newValue.cover.await()

                onUI {
                    background = Background(
                        BackgroundImage(
                            coverCache[cardCache[cacheIndex]]?:
                            Image(stream).apply {
                                coverCache[cardCache[cacheIndex]] = this
                            },
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.CENTER,
                            BackgroundSize(1.0,1.0,true,true,true,true)
                        )
                    )
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
            setAlignment(this, Pos.BOTTOM_LEFT)
        }

        hbox {
            val leftButton = button("<") {
                action {
                    println(cacheIndex)
                    if (cacheIndex == 0) return@action

                    currentCard = cardCache[--cacheIndex]
                }

                stylesheets += "css/node/suspended-button.css"
                style(true) {
                    textFill = Color.WHITE
                }

                hgrow = Priority.ALWAYS
            }

            val rightButton = button(">") {
                newIO {
                    card.collect {
                        cardCache += it
                    }
                    onUI { currentCard = cardCache.first() }

                    suspendEventHandler(MouseEvent.MOUSE_CLICKED) {
                        currentCard = cardCache[++cacheIndex]
                    }
                }

                stylesheets += "css/node/suspended-button.css"
                style(true) {
                    textFill = Color.WHITE
                }
                hgrow = Priority.ALWAYS
            }

            Bindings.createDoubleBinding({
                leftButton.width + rightButton.width
            },leftButton.widthProperty(), rightButton.widthProperty()).run {
                maxWidthProperty().bind(this)
            }

            Bindings.createDoubleBinding({
                leftButton.height.coerceAtLeast(rightButton.height)
            },leftButton.heightProperty(), rightButton.heightProperty()).run {
                maxHeightProperty().bind(this)
            }

            style {
                backgroundColor += c("black",0.5)
                backgroundRadius += box(5.px)
            }

            setAlignment(this, Pos.BOTTOM_LEFT)
            setMargin(this, insets(0, 0, 50, 50))
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