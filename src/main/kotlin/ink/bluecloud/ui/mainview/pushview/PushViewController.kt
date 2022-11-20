package ink.bluecloud.ui.mainview.pushview

import ink.bluecloud.ui.animationTo
import javafx.scene.layout.Pane
import tornadofx.*
import kotlin.math.absoluteValue

class PushViewController: Controller() {

    init {
        (params["HomeView"]as PushView).apply {
            pushPane.apply {
                var cardWidth: Double? = null

                widthProperty().addListener { _, _, newValue ->
                    if (children.size == 0) return@addListener

                    cardWidth?: run {
                        cardWidth =(children[0] as Pane).width.run {
                            if (this == 0.0) return@addListener
                            this
                        }
                    }

                    val paneWidth = newValue.toDouble()
                    val cardCount = ((paneWidth - 20.0) / cardWidth!!).toInt().run {
                        if ((paneWidth - 20.0) - ((this - 1) * hgap) - (this * cardWidth!!) < 0) {
                            this - 1
                        } else {
                            this
                        }
                    }

                    if ((paneWidth + 20) >= ((cardWidth!! * cardCount) + ((cardCount - 1) * 10))) {
                        (paneWidth - (cardCount * cardWidth!!) + ((cardCount - 1) * 10)) / (cardCount + 1)
                    } else {
                        10.0
                    }.run {
                        if ((this - hgap).absoluteValue < 10.0) {
                            hgap = this
                        } else animationTo(hgapProperty(), endValue = this@run)
                    }
                }

/*
                dispatcher.service<PushServiceProviderImpl, HomeViewPushService> {
                    uiScope.launch {
                        repeat(50) {
                            children += PushDisplayCard(getCard())
                        }
                    }
                }
*/
            }
        }
    }
}