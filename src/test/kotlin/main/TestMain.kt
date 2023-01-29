package main

import build.buildAPI
import ink.bluecloud.model.data.cookie.CookieJson
import ink.bluecloud.model.data.video.HomePagePushCard
import ink.bluecloud.service.cookie.CookieUpdate
import ink.bluecloud.service.seeting.SettingCenterImpl
import ink.bluecloud.service.user.AccountInfo
import ink.bluecloud.service.user.relationship.UserCommonFollow
import ink.bluecloud.service.video.portal.PortalVideoList
import ink.bluecloud.utils.format
import ink.bluecloud.utils.toDate
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
import org.koin.ksp.generated.ink_bluecloud_model_networkapi_NetWorkApiModule
import org.koin.ksp.generated.ink_bluecloud_network_NetWorkModel
import org.koin.ksp.generated.ink_bluecloud_service_ServiceModule
import org.koin.ksp.generated.ink_bluecloud_ui_UIModule
import org.koin.logger.slf4jLogger
import org.slf4j.LoggerFactory
import java.lang.Thread.sleep
import kotlin.system.exitProcess

class TestRun {
    suspend fun run() {
        val mid = AccountInfo().getAccountInfo().mid
        UserCommonFollow().getCommonFollowers(mid).data?.list?.forEach {
            println(it.uname)
        }
    }

    suspend fun stop(d:Long = 3000) {
        withContext(Dispatchers.IO) {
            sleep(d)
        }
        exitProcess(0)
    }
}


suspend fun getBvId(): HomePagePushCard {
    val videoList = PortalVideoList().getVideoList()
    println(videoList.first())
    return videoList.first()
}

suspend fun main() {
    buildAPI()
    init()
    loadCookies()
    run()
}
private suspend fun run() {
    val run = TestRun()
    runCatching { run.run() }.onFailure {
        LoggerFactory.getLogger("TestMain").error("测试错误\n", it)
        run.stop(0)
    }
    run.stop()
}
private fun loadCookies(){
//    CookieUpdate().loadCookie(
//        "buvid3=EA0065A3-95D6-CAD9-9E09-4ED16B24179D81102infoc; i-wanna-go-back=-1; _uuid=2D48DF69-A4C3-10810D-258D-35C479217B6C81620infoc; buvid_fp_plain=undefined; CURRENT_BLACKGAP=0; LIVE_BUVID=AUTO8616563345409790; blackside_state=0; nostalgia_conf=-1; hit-dyn-v2=1; is-2022-channel=1; b_nut=100; buvid4=CF1C91FF-58CF-BBA6-4A48-FD3E4333F79082040-022062720-pi29DxjWV7cTCy1mnn/G7w==; fingerprint3=835fd079c23731c2fb89f1d9ef9f3bd6; DedeUserID=204700919; DedeUserID__ckMd5=427a3d38a2f2f73b; b_ut=5; CURRENT_FNVAL=4048; rpdid=|(k|~llkuk))0J'uYY)YluJu~; CURRENT_QUALITY=80; hit-new-style-dyn=0; fingerprint=140ecfe1b08139144796d8e6039e98df; SESSDATA=d73846d5,1690430901,84971*11; bili_jct=6215810d78f4b6ea1df21a608bba28f5; bp_video_offset_204700919=756100817706025000; innersign=0; b_lsid=399D9ED4_185F746126E; buvid_fp=140ecfe1b08139144796d8e6039e98df; PVID=2"
//    )

    val cookieJson = SettingCenterImpl().loadSetting(CookieJson::class.java)
    cookieJson?.update()


    println("============================Cookies================================")
    println(CookieUpdate().getCookieStore().toCookies().toString())
    println("timestamp="+CookieUpdate().getCookieStore().toCookies().timestamp.toDate().format("yyyy-MM-dd HH:mm:ss.SSS"))
    println("--------------------------------------------------------------------")
    println(CookieUpdate().getCookieStore().toCookies().toWebCookie())
    println("============================Cookies================================")
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
            )
        }

        modules(parentModule)
        slf4jLogger(Level.INFO)
    }
}