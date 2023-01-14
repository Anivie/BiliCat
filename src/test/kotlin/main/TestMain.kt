package main

import build.buildAPI
import ink.bluecloud.model.data.cookie.CookieJson
import ink.bluecloud.model.data.video.HomePagePushCard
import ink.bluecloud.service.account.cookie.CookieUpdate
import ink.bluecloud.service.seeting.SettingCenterImpl
import ink.bluecloud.service.user.AccountInfo
import ink.bluecloud.service.user.relationship.UserAttention
import ink.bluecloud.service.video.hot.VideoWeeklyList
import ink.bluecloud.service.video.portal.PortalVideoList
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
import java.lang.Thread.sleep
import kotlin.system.exitProcess

class TestRun {
    suspend fun run() {
        UserAttention().getAttentionV2(204700919).data!!.list.forEach {
            println("${it.uname}: ${it.special}")
        }
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
    CookieUpdate().loadCookie(
        "bili_jct=86bf89d899dd4aab29aa5e5c5d81292c;DedeUserID=204700919;gourl=http%3A%2F%2Fwww.bilibili.com;DedeUserID__ckMd5=427a3d38a2f2f73b;SESSDATA=aedc3fd4%2C1688885033%2C42a5c%2A11;"
    )
    println(CookieUpdate().getCookieStore().toCookies())
//    val cookieJson = SettingCenterImpl().loadSetting(CookieJson::class.java)
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
            )
        }

        modules(parentModule)
        slf4jLogger(Level.INFO)
    }
}