package ink.bluecloud

import javafx.application.Application
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.javafx.JavaFx
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ksp.generated.ink_bluecloud_model_networkapi_NetWorkApiModule
import org.koin.ksp.generated.ink_bluecloud_network_NetWorkModel
import org.koin.ksp.generated.ink_bluecloud_service_ServiceModule
import org.koin.ksp.generated.ink_bluecloud_ui_UIModule
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) {
    val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println(coroutineContext)
        throwable.printStackTrace()
    }

    startKoin {
        val parentModule = module {
            single(named("uiScope")) {
                CoroutineScope(Dispatchers.JavaFx + errorHandler)
            }
            single(named("ioScope")) {
                CoroutineScope(Dispatchers.IO + errorHandler)
            }

            includes(
                ink_bluecloud_ui_UIModule,
                ink_bluecloud_network_NetWorkModel,
                ink_bluecloud_service_ServiceModule,
                ink_bluecloud_model_networkapi_NetWorkApiModule
            )
        }

        modules(parentModule)
        slf4jLogger(Level.INFO)
    }

    System.setProperty("prism.lcdtext", "false")
    Application.launch(MainApp::class.java,*args)
}