@file:Suppress("NOTHING_TO_INLINE")

package ink.bluecloud.utils.uiutil

import javafx.geometry.Point2D
import javafx.scene.Node

inline fun Node.nodeToScene():Point2D {
    val localCoordinates = localToScene(0.0, 0.0)
    val rootCoordinates = scene.root.localToScene(0.0, 0.0)
    return Point2D(localCoordinates.x - rootCoordinates.x,localCoordinates.y - rootCoordinates.y)
}