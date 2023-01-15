package ink.bluecloud.ui.mainview.node

import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import org.koin.core.component.KoinComponent
import tornadofx.*

class ExperienceBar(
    level: Int
): HBox(), KoinComponent {
    init {
        val color = c(0,174,236)
        val actionColor = c(251,114,153)

        region {
            style {
                backgroundColor += color
                backgroundRadius += box(5.px, 0.px, 0.px, 5.px)
            }

            minHeight = 10.0
            hgrow = Priority.ALWAYS
        }

        repeat(4) {
            region {
                style {
                    backgroundColor += color
                }

                minHeight = 10.0
                hgrow = Priority.ALWAYS
            }
        }

        region {
            style {
                backgroundColor += color
                backgroundRadius += box(0.px, 5.px, 5.px, 0.px)
            }

            minHeight = 10.0
            hgrow = Priority.ALWAYS
        }

        repeat(level) {
            children[it].style(true) {
                backgroundColor += actionColor
            }
        }

        spacing = 3.0
    }
}