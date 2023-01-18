package ink.bluecloud.ui.mainview.node

import ink.bluecloud.cloudtools.CLOUD_INTERPOLATOR
import ink.bluecloud.model.data.account.AccountCard
import ink.bluecloud.utils.newUI
import ink.bluecloud.utils.uiutil.cloudTimeline
import ink.bluecloud.utils.uiutil.nodeToScene
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Pos
import javafx.scene.effect.BlurType
import javafx.scene.effect.DropShadow
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.transform.Scale
import javafx.util.Duration
import kotlinx.coroutines.delay
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent
import tornadofx.*

@Single
class SuspensionProFileCard(
    open: SimpleBooleanProperty,
    box: Pane
): VBox(), KoinComponent {
    val accountInfo = SimpleObjectProperty<AccountCard>()

    init {
        val uidLabel = label()

        accountInfo.addListener { _, _, newValue ->
            uidLabel.run {
                style {
                    textFill = c("gray")
                    fontSize = 10.px
                }
                text = ">=====${newValue.mid}=====<"
            }

        }

        setOnMouseExited {
            open.value = false
        }

        style {
            backgroundColor += Color.WHITE
            backgroundRadius += box(20.px)
        }

        initPosition(box)

        alignment = Pos.TOP_CENTER

        maxWidth = 200.0
        maxHeight = 300.0
        opacity = 0.0
        transforms += playAnimation(box, open)
        effect = DropShadow(BlurType.GAUSSIAN, c(0,0,0,0.2), 20.0,0.0,7.0,10.0)
    }

    private fun playAnimation(
        box: Pane,
        open: SimpleBooleanProperty,
    ): Scale {
        val scale = Scale(1.0, 0.5)
        val rootChildren = (box.scene.root as Pane).children

        val inAnimation = cloudTimeline {
            keyframe(Duration.millis(150.0)) {
                keyvalue((box.effect as DropShadow).colorProperty(), c(0, 0, 0, 0.25), CLOUD_INTERPOLATOR)
                keyvalue(box.translateXProperty(), -5, CLOUD_INTERPOLATOR)
                keyvalue(box.translateYProperty(), -2, CLOUD_INTERPOLATOR)

                keyvalue(opacityProperty(), 1.0, CLOUD_INTERPOLATOR)
                keyvalue(scale.yProperty(), 1.0, CLOUD_INTERPOLATOR)
            }
        }

        val outAnimation = cloudTimeline {
            keyframe(Duration.millis(150.0)) {
                keyvalue((box.effect as DropShadow).colorProperty(), c(0, 0, 0, 0.1), CLOUD_INTERPOLATOR)
                keyvalue(box.translateXProperty(), 0, CLOUD_INTERPOLATOR)
                keyvalue(box.translateYProperty(), 0, CLOUD_INTERPOLATOR)

                keyvalue(opacityProperty(), 0.0, CLOUD_INTERPOLATOR)
                keyvalue(scale.yProperty(), 0.5, CLOUD_INTERPOLATOR)
            }
        }

        open.addListener { _, _, newValue ->
            if (newValue) {
                inAnimation.play()
            } else {
                if (rootChildren.contains(this)) {
                    newUI {
                        outAnimation.play()
                        delay(150)
                        rootChildren -= this@SuspensionProFileCard
                    }
                }
            }
        }
        return scale
    }

    private fun initPosition(box: Pane) {
        val distance = box.nodeToScene()
        StackPane.setAlignment(this, Pos.TOP_LEFT)
        StackPane.setMargin(this, insets(distance.y + box.height, 0, 0, distance.x - 25))
    }
}