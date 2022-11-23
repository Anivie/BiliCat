package ink.bluecloud.utils

import javafx.scene.Node
import javafx.scene.layout.StackPane

inline val Node.sceneRoot
    get() = scene.root as StackPane