package ink.bluecloud.ui

import org.koin.core.component.KoinComponent
import tornadofx.*

abstract class CloudController<T: View>: KoinComponent {
    abstract fun initUi(view: T)
}