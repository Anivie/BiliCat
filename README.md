![](screenshot/home.png)

 - 测试流程：  
   - Fork仓库
   - 在idea运行配置中添加VM参数
   - 运行`ink.bluecloud.MainKt#main`

VM参数：
```
   --add-opens=javafx.controls/javafx.scene.control.skin=ALL-UNNAMED --add-opens=javafx.media/javafx.scene.media=ALL-UNNAMED --add-opens=java.xml.crypto/com.sun.org.slf4j.internal=ALL-UNNAMED --add-opens=javafx.media/com.sun.media.jfxmedia.locator=ALL-UNNAMED -javaagent:libs\BiliBiliFX-Agent.jar -Dkotlinx.coroutines.debug
```
