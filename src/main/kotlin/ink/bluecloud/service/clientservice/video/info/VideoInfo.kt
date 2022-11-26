package ink.bluecloud.service.clientservice.video.info


import ink.bluecloud.model.data.video.Video
import ink.bluecloud.model.pojo.video.info.VideoInfoJsonRoot
import ink.bluecloud.service.ClientService
import ink.bluecloud.utils.getForString
import ink.bluecloud.utils.param
import ink.bluecloud.utils.toObjJson
import org.koin.core.annotation.Factory

/**
 * 获取视频具体信息
 * @Guess: 此接口会自动调用获取视频流链接API
 * @TestAPI: 等待测试API稳定后移除此项
 */
@Factory
class VideoInfo : ClientService() {
    /**
     * 获取视频信息
     */
    suspend fun getVideoInfo(bvid: String): Video? {
        val pojo = getJsonPOJO(bvid)

        if (pojo.code != 0 || pojo.data == null) {
            return null
        }
        val data = pojo.data

        //构建pages
        val pages0 = getPages(data)
        //构建CIDs
        val cids0 = ArrayList<Long>()
        pages0.forEach { cid0 -> cids0.add(cid0.cid) }

        //构建Video类型
        return Video(
            avid = data.aid,//avid
            bvid = data.bvid,//bvid
            cid = data.cid,//cid, 视频的page的定位符
            title = data.title,//视频标题
            duration = data.duration,//视频时长单位s
            cover = data.pic,//视频封面url
            cids = cids0,//视频所有的page的cid
            stat = Video.Stat(
                //视频的状态，点赞等
                view = data.stat.view,
                like = data.stat.like,
                argue_msg = data.stat.argue_msg,
                coin = data.stat.coin,
                danmaku = data.stat.danmaku,
                evaruation = data.stat.evaluation,
                favorite = data.stat.favorite,
                hisRank = data.stat.his_rank,
                nowRank = data.stat.now_rank,
                share = data.stat.share,

                ),
            type = Video.Type(type = data.copyright),//视频转发或原创
            dimension = Video.Dimension(//视频尺寸，宽高
                width = data.dimension.width,
                height = data.dimension.height,
                rotate = data.dimension.rotate
            ),
            time = Video.Time(//视频发布时间
                publishDate = data.pubdate,
                uploadDate = data.ctime
            ),
            page = Video.PageEntrance(//视频分P
                pages = pages0
            ),
            CCSubtitle = data.subtitle?.let { sub ->//cc字幕
                Video.Subtitle(
                    allow_submit = sub.allow_submit,
                    list = getCC(data) ?: throw IllegalArgumentException()
                )
            },
            authorName = data.owner.name,//up名称
            authorMid = data.owner.mid,//up 的mid
            authorFace = data.owner.face,//up头像
            authors = getStaff(data),//合作up列表《名称，MID》
            describe = Video.Describe(//简介
                describe = data.desc,
                describeV2 = getDescribeV2(data)
            )
        )
    }

    /**
     * 构建简介第二版
     */
    private fun getDescribeV2(data: VideoInfoJsonRoot.Data): ArrayList<Video.DescV2>? {
        return data.desc_v2?.mapTo(ArrayList()) {
            Video.DescV2(
                biz_id = it.biz_id,
                raw_text = it.raw_text,
                type = it.type
            )
        }
    }

    /**
     * 构建合作列表
     */
    private fun getStaff(data: VideoInfoJsonRoot.Data): ArrayList<Video.StaffItem>? {
        return data.staff?.mapTo(ArrayList()) {
            Video.StaffItem(
                mid = it.mid,
                face = it.face,
                follower = it.follower,
                name = it.name,
                position = it.title
            )
        }
    }

    /**
     * 构建CCSubtitle
     */
    private fun getCC(data: VideoInfoJsonRoot.Data): ArrayList<Video.CC>? {
        //构建CCSubtitle
        return data.subtitle?.list?.mapTo(ArrayList()) {
            Video.CC(
                id = it.id,
                lan = it.lan,
                lanDoc = it.lan_doc,
                is_lock = it.is_lock,
                url = it.subtitle_url,
                type = it.type,
                authorMid = when (it.author_mid) {
                    null -> it.author?.mid
                    else -> it.author_mid
                },
                aiStatus = it.ai_status,
                aiType = it.ai_type
            )
        }
    }

    /**
     * 构建page
     */
    private fun getPages(data: VideoInfoJsonRoot.Data): ArrayList<Video.Page> {
        //构建page
        return data.pages.mapTo(ArrayList()) {
            Video.Page(
                cid = it.cid,
                duration = it.duration,
                from = it.from,
                dimension = Video.Dimension(
                    height = it.dimension.height,
                    rotate = it.dimension.rotate,
                    width = it.dimension.width
                ),
                page = it.page,
                weblink = it.weblink,
                title = it.part,
                vid = it.vid,
            )
        }
    }

    /**
     * 获取详细数据
     */
    private suspend fun getJsonPOJO(bvid: String): VideoInfoJsonRoot.Root {
        val param = netWorkResourcesProvider.api.getVideoINFO.param {
            it["bvid"] = bvid
        }
        logger.debug("API Get VideoInfo -> $param")
        return httpClient.getForString(
            param
        ).toObjJson(VideoInfoJsonRoot.Root::class.java)
    }
}