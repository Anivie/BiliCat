package ink.bluecloud

import ink.bluecloud.cloudtools.cloudnotice.CloudNotice
import ink.bluecloud.cloudtools.cloudnotice.Property.NoticeType
import javafx.application.Application
import javafx.stage.Stage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.javafx.JavaFx
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ksp.generated.*
import org.koin.logger.slf4jLogger
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.io.PrintWriter
import java.security.Security


fun main() {
    val logger = LoggerFactory.getLogger("CoroutineErrorHandler")

    startKoin {
        val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            val stream = ByteArrayOutputStream(1000)

            PrintWriter(stream).use {
                throwable.printStackTrace(it)
            }

            val err = buildString {
                append("\nHava a error on coroutine:${coroutineContext}\n")
                append(String(stream.toByteArray()))
                append("---By global coroutine error handler---")
            }

            val window = koin.getProperty<Stage>("primaryStage")?: throw IllegalArgumentException("An error occur but primary stage not register.")
            CloudNotice(NoticeType.Error,throwable.message ?: "Unknown Error on $throwable", window).show()
            logger.error(err)
        }

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
    Security.addProvider(BouncyCastleProvider())
    Application.launch(MainApp::class.java)
}