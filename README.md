![](screenshot/home.png)

### 运行环境：
本项目使用最新LTS版本的JDK进行开发，当前为JDK17.  
使用Zulu17开发，经测试可以运行于Zulu17,Liberica17,IBM17  
JavaFX版本使用最新稳定版  

 - 测试流程：  
   - Clone仓库
   - 在idea运行配置中添加VM参数
   - 运行`ink.bluecloud.MainKt#main`

VM参数：
```
   --add-opens=javafx.controls/javafx.scene.control.skin=ALL-UNNAMED --add-opens=javafx.media/javafx.scene.media=ALL-UNNAMED --add-opens=java.xml.crypto/com.sun.org.slf4j.internal=ALL-UNNAMED --add-opens=javafx.media/com.sun.media.jfxmedia.locator=ALL-UNNAMED -javaagent:libs\BiliBiliFX-Agent.jar -Dkotlinx.coroutines.debug
```
