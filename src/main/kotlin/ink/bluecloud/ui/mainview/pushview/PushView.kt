package ink.bluecloud.ui.mainview.pushview

import tornadofx.*

class PushView: PushViewNodes() {
    override val root = scrollpane {
        pushPane = flowpane {
            vgap = 15.0
            hgap = 20.0
        }

        style {
            backgroundColor += c(249, 249, 249)
            backgroundRadius += box(10.px)
        }

        isFitToWidth = true
        paddingAll = 10
    }

    override fun onDock() {
//        find<PushViewController>(params = mapOf("HomeView" to this))
    }
}