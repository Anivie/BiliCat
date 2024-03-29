package ink.bluecloud

import ink.bluecloud.cloudtools.stageinitializer.initCustomizeStageAndRoot
import ink.bluecloud.css.themes
import ink.bluecloud.network.http.HttpClient
import ink.bluecloud.service.init.LoadCookie
import ink.bluecloud.ui.mainview.MainView
import ink.bluecloud.utils.newIO
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.stopKoin
import tornadofx.*
import java.io.File
import kotlin.reflect.KClass

class MainApp : App(
    icon = Image("ui/icon.png"),
    primaryView = MainView::class
),KoinComponent {

    init {
        System.getProperty("intellij.debug.agent")?.run {
            reloadViewsOnFocus()
            reloadStylesheetsOnFocus()
        }

        FX.dicontainer = object :DIContainer {
            override fun <T : Any> getInstance(type: KClass<T>): T {
                return getKoin().get(type)
            }
        }

        newIO {
            get<LoadCookie>().load()

            File("config").run {
                if (!exists()) mkdir()
            }
            File("cache").run {
                if (!exists()) mkdir()
            }
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
        get<HttpClient>().close()
        stopKoin()
    }
}