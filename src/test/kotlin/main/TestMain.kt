package main

import ink.bluecloud.model.data.cookie.CookieJson
import ink.bluecloud.service.clientservice.account.cookie.CookieUpdate
import ink.bluecloud.utils.settingloader.SettingCenterImpl
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.withContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ksp.generated.*
import org.koin.logger.slf4jLogger
import java.lang.Thread.sleep
import kotlin.system.exitProcess

suspend fun main() {
    init()
//    CookieUpdate().loadCookie("PVID=1; \n\n buvid3=F10A9E92-166F-D7B0-9492-7A294A427BF241784infoc; \n \n i-wanna-go-back=-1; \n _uuid=A1018A103F-9DFD-7F110-5166-9106B33CDDA10743106infoc; \n b_nut=100; \n fingerprint=17deff6fd7526e216ffda059d2f8e5b5; \n buvid_fp_plain=undefined; \n buvid_fp=9a65cb139bc7d5fce21895a86694fc41; \n fingerprint3=835fd079c23731c2fb89f1d9ef9f3bd6; \n b_ut=5; \n nostalgia_conf=-1; \n CURRENT_BLACKGAP=0; \n CURRENT_FNVAL=4048; \n bp_video_offset_102664436=729792824416927700; \n DedeUserID=701615499; \n DedeUserID__ckMd5=470d902031e88387; \n rpdid=|(k|mlmJYkuJ0J'uYY)~l)YRJ; \n CURRENT_QUALITY=127; \n buvid4=51636527-B758-B365-5058-48023AA5203012762-022062715-pi29DxjWV7cTCy1mnn/G7w==; \n innersign=0; \n b_lsid=E9BBB764_184A2AE961A; \n bp_video_offset_701615499=731594425762840800; \n SESSDATA=c57f39ea,1684728663,efdc4*b1; \n bili_jct=13542a2292e970d71fcc8dc0fe41e575; \n sid=npcbdq9w")
    val cookieJson = SettingCenterImpl().readSettingOnly(CookieJson::class.java)
    cookieJson?.update()
    TestRun().run()
    withContext(Dispatchers.IO) {
        sleep(3000)
    }
    exitProcess(0)
}

private fun init() {
    val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        System.err.println("Hava a error on coroutine:${coroutineContext}")
        throwable.printStackTrace()
        System.err.println("by coroutine error handler")
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
                ink_bluecloud_model_networkapi_NetWorkApiModule,
                ink_bluecloud_utils_settingloader_SettingModule
            )
        }

        modules(parentModule)
        slf4jLogger(Level.INFO)
    }
}