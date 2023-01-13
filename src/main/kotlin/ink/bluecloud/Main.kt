package ink.bluecloud

import ink.bluecloud.cloudtools.cloudnotice.CloudNotice
import ink.bluecloud.cloudtools.cloudnotice.Property.NoticeType
import javafx.application.Application
import javafx.stage.Stage
import kotlinx.coroutines.*
import kotlinx.coroutines.javafx.JavaFx
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ksp.generated.ink_bluecloud_model_networkapi_NetWorkApiModule
import org.koin.ksp.generated.ink_bluecloud_network_NetWorkModel
import org.koin.ksp.generated.ink_bluecloud_service_ServiceModule
import org.koin.ksp.generated.ink_bluecloud_ui_UIModule
import org.koin.logger.slf4jLogger
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.io.PrintWriter
import java.security.Security


fun main() {
    val logger = LoggerFactory.getLogger("CoroutineErrorHandler")

    initKoin(logger)

    initSystemProperty()

    Security.addProvider(BouncyCastleProvider())
    Application.launch(MainApp::class.java)
}

private fun initSystemProperty() {
    System.setProperty("prism.lcdtext", "false")
}

private fun initKoin(logger: Logger) {
    startKoin {
        val errorHandler = coroutineExceptionHandler(logger)

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
                ink_bluecloud_model_networkapi_NetWorkApiModule
            )
        }

        modules(parentModule)
        slf4jLogger(Level.INFO)
    }
}

private fun KoinApplication.coroutineExceptionHandler(logger: Logger): CoroutineExceptionHandler {
    val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        val stream = ByteArrayOutputStream(1000)

        PrintWriter(stream).use {
            throwable.printStackTrace(it)
        }

        val err = buildString {
            coroutineContext[CoroutineName]?.run {
                append("\nHava a error on coroutine:${name}\n")
            }?: append("\nHava a error on coroutine.\n")
            append("\nIt's context:${coroutineContext}\n")
            append(String(stream.toByteArray()))
            append("---By global coroutine error handler---")
        }

        val window = koin.getProperty<Stage>("primaryStage")
            ?: throw IllegalArgumentException("An error occur but primary stage not register.")
        koin.get<CoroutineScope>(named("uiScope")).launch {
            CloudNotice(NoticeType.Error, throwable.message ?: "Unknown Error on $throwable", window).show()
        }
        logger.error(err)
    }
    return errorHandler
}