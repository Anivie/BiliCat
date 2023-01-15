package ink.bluecloud.ui.mainview.node

import ink.bluecloud.cloudtools.CLOUD_INTERPOLATOR
import ink.bluecloud.utils.ui
import ink.bluecloud.utils.uiutil.cloudTimeline
import ink.bluecloud.utils.uiutil.nodeToScene
import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Pos
import javafx.scene.effect.BlurType
import javafx.scene.effect.DropShadow
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.transform.Scale
import javafx.util.Duration
import kotlinx.coroutines.delay
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent
import tornadofx.*

@Single
class ProFileCard(
    open: SimpleBooleanProperty,
    box: Pane
): StackPane(), KoinComponent {
    init {
        val rootChildren = (box.scene.root as Pane).children

        setOnMouseExited {
            open.value = false
        }

        val scale = Scale(1.0,0.5)

        val inAnimation = cloudTimeline {
            keyframe(Duration.millis(150.0)) {
                keyvalue((box.effect as DropShadow).colorProperty(),c(0,0,0,0.25), CLOUD_INTERPOLATOR)
                keyvalue(opacityProperty(),1.0, CLOUD_INTERPOLATOR)
                keyvalue(scale.yProperty(),1.0, CLOUD_INTERPOLATOR)
            }
        }

        val outAnimation = cloudTimeline {
            keyframe(Duration.millis(150.0)) {
                keyvalue((box.effect as DropShadow).colorProperty(),c(0,0,0,0.1), CLOUD_INTERPOLATOR)
                keyvalue(opacityProperty(),0.0, CLOUD_INTERPOLATOR)
                keyvalue(scale.yProperty(),0.5, CLOUD_INTERPOLATOR)
            }
        }

        open.addListener { _, _, newValue ->
            if (newValue) {
                inAnimation.play()
            }else {
                if (rootChildren.contains(this)) {
                    ui {
                        outAnimation.play()
                        delay(150)
                        rootChildren -= this@ProFileCard
                    }
                }
            }
        }

        val distance = box.nodeToScene()
        setAlignment(this, Pos.TOP_LEFT)
        setMargin(this, insets(distance.y + box.height, 0, 0, distance.x - 10))

        style {
            backgroundColor += Color.WHITE
            backgroundRadius += box(20.px)
        }

        maxWidth = 200.0
        maxHeight = 300.0

        opacity = 0.0
        transforms += scale

        effect = DropShadow(BlurType.GAUSSIAN, c(0,0,0,0.2), 20.0,0.0,7.0,10.0)
    }
}