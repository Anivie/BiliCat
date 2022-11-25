package main

import javafx.application.Application
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.stage.Stage
import java.io.File

class AudioTest : Application() {
    override fun start(primaryStage: Stage?) {
        MediaPlayer(Media(File("D:\\IDEA_WorkSpace\\BiliCat\\.idea\\httpRequests\\2022-11-26T001901.200.mp4").toURI().toString())).play()
    }
}

fun main() {
    Application.launch(AudioTest::class.java)
}