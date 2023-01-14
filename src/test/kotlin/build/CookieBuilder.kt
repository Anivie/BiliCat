package build

import ink.bluecloud.network.http.CookieManager
import ink.bluecloud.service.seeting.SettingCenterImpl
import ink.bluecloud.service.seeting.saveSetting

fun main() {
    val cookie = CookieManager()
        .apply { parseAllTo(
            "buvid3=EA0065A3-95D6-CAD9-9E09-4ED16B24179D81102infoc; i-wanna-go-back=-1; _uuid=2D48DF69-A4C3-10810D-258D-35C479217B6C81620infoc; buvid_fp_plain=undefined; CURRENT_BLACKGAP=0; LIVE_BUVID=AUTO8616563345409790; blackside_state=0; nostalgia_conf=-1; hit-dyn-v2=1; is-2022-channel=1; b_nut=100; buvid4=CF1C91FF-58CF-BBA6-4A48-FD3E4333F79082040-022062720-pi29DxjWV7cTCy1mnn/G7w==; fingerprint3=835fd079c23731c2fb89f1d9ef9f3bd6; DedeUserID=204700919; DedeUserID__ckMd5=427a3d38a2f2f73b; b_ut=5; CURRENT_FNVAL=4048; rpdid=|(k|~llkuk))0J'uYY)YluJu~; fingerprint=876bd58ef238d24f3d8f3c838a4bd365; CURRENT_QUALITY=80; hit-new-style-dyn=0; buvid_fp=876bd58ef238d24f3d8f3c838a4bd365; PVID=3; bp_video_offset_204700919=749902213502992400; SESSDATA=eefb8c33,1689074318,afa77*11; bili_jct=87d5983f90435c00755ae7392fa37334; innersign=0; b_lsid=1093F103B9_185AA8E62C4; sid=n3zs8m1k"
        ) }
        .toCookies()
    println(cookie)
    SettingCenterImpl().saveSetting(cookie)
}