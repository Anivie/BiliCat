package main

import ink.bluecloud.model.data.video.HomePagePushCard
import ink.bluecloud.service.clientservice.account.cookie.CookieUpdate
import ink.bluecloud.service.clientservice.barrage.HistoricalBarret
import ink.bluecloud.service.clientservice.barrage.RealTimeBarrage
import ink.bluecloud.service.clientservice.user.AccountInfo
import ink.bluecloud.service.clientservice.video.portal.PortalVideoList
import ink.bluecloud.utils.format
import ink.bluecloud.utils.isDate
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.withContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ksp.generated.*
import org.koin.logger.slf4jLogger
import java.lang.Thread.sleep
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess

class TestRun {
    suspend fun run() {
        println(AccountInfo().getAccountInfo())
//        HistoricalBarret().getHistoricalBarretDateAll(333387223).forEach {
//            println(it)
//            HistoricalBarret().getHistoricalBarret(333387223,it).forEach { barret->
//                println("${Date(barret.sendTime)}: ${barret.content}")
//            }
//        }
    }
}


suspend fun getBvId(): HomePagePushCard {
    val videoList = PortalVideoList().getVideoList()
    println(videoList.first())
    return videoList.first()
}

suspend fun main() {
    init()
//    CookieUpdate().loadCookie("buvid3=EA0065A3-95D6-CAD9-9E09-4ED16B24179D81102infoc; i-wanna-go-back=-1; _uuid=2D48DF69-A4C3-10810D-258D-35C479217B6C81620infoc; buvid_fp_plain=undefined; CURRENT_BLACKGAP=0; LIVE_BUVID=AUTO8616563345409790; blackside_state=0; nostalgia_conf=-1; hit-dyn-v2=1; is-2022-channel=1; b_nut=100; buvid4=CF1C91FF-58CF-BBA6-4A48-FD3E4333F79082040-022062720-pi29DxjWV7cTCy1mnn%2FG7w%3D%3D; fingerprint3=835fd079c23731c2fb89f1d9ef9f3bd6; DedeUserID=204700919; DedeUserID__ckMd5=427a3d38a2f2f73b; b_ut=5; CURRENT_FNVAL=4048; rpdid=|(k|~llkuk))0J'uYY)YluJu~; fingerprint=876bd58ef238d24f3d8f3c838a4bd365; CURRENT_QUALITY=80; PVID=1; buvid_fp=876bd58ef238d24f3d8f3c838a4bd365; bp_video_offset_204700919=748693502324375600; hit-new-style-dyn=0; b_lsid=108A74654_18590FD0FA8; SESSDATA=cee7ec7b%2C1688726784%2Ccd89a%2A11; bili_jct=f02b902765d7a267f3dcb9b8cc6d8b3c; sid=6kg2d0ji; innersign=1; theme_style=light")
//    val cookieJson = SettingCenterImpl().readSettingOnly(CookieJson::class.java)
//    cookieJson?.update()
    runCatching { TestRun().run() }.onFailure { it.printStackTrace() }

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