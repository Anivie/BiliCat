package ink.bluecloud

import ink.bluecloud.cloudtools.stageinitializer.initCustomizeStageAndRoot
import ink.bluecloud.css.themes
import ink.bluecloud.network.http.HttpClient
import ink.bluecloud.service.ClientService
import ink.bluecloud.ui.mainview.MainView
import ink.bluecloud.utils.koin
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.javafx.JavaFx
import org.koin.core.component.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.ksp.generated.ink_bluecloud_service_ServiceModule
import org.koin.ksp.generated.ink_bluecloud_ui_UIModule
import org.koin.logger.slf4jLogger
import tornadofx.*

class MainApp: App(
    icon = Image("ui/icon.png"),
//    primaryView = LoginView::class,
    primaryView = MainView::class,
),KoinComponent {

    init {
        System.getProperty("intellij.debug.agent")?.run {
            reloadViewsOnFocus()
            reloadStylesheetsOnFocus()
        }
    }

    override fun start(stage: Stage) {
        super.start(stage.initCustomizeStageAndRoot(15))
    }

    override fun createPrimaryScene(view: UIComponent): Scene {
        return super.createPrimaryScene(view).apply {
            themes {
                it += globalCssFile
                it += buttonCssFile
            }

            stylesheets += "css/font/font.css"
        }
    }

    override fun stop() {
        stopKoin()
        super.stop()
    }
}