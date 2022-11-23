package ink.bluecloud

import javafx.application.Application
import kotlinx.coroutines.*
import kotlinx.coroutines.javafx.JavaFx
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ksp.generated.*
import org.koin.logger.slf4jLogger
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.io.PrintWriter

class Main
fun main(args: Array<String>) {
    val logger = LoggerFactory.getLogger(Main::class.java)
    val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        coroutineContext.cancel()
        val stream = ByteArrayOutputStream(1000)

        PrintWriter(stream).use {
            throwable.printStackTrace(it)
        }

        val err = buildString {
            append("\nHava a error on coroutine:${coroutineContext}\n")
            append(String(stream.toByteArray()))
            append("---By coroutine error handler---")
        }

        logger.error(err)
    }

    startKoin {
        val parentModule = module {
            single(named("uiScope")) {
                CoroutineScope(Dispatchers.JavaFx + errorHandler + SupervisorJob())
            }
            single(named("ioScope")) {
                CoroutineScope(Dispatchers.IO + errorHandler + SupervisorJob())
            }

            includes(
                ink_bluecloud_ui_UIModule,
                ink_bluecloud_network_NetWorkModel,
                ink_bluecloud_service_ServiceModule,
                ink_bluecloud_model_networkapi_NetWorkApiModule,
                ink_bluecloud_utils_settingloader_SettingModule
            )
        }

        modules(parentModule)
        slf4jLogger(Level.INFO)
    }

    System.setProperty("prism.lcdtext", "false")
    Application.launch(MainApp::class.java,*args)
}