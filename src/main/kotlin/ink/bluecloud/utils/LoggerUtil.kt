@file:Suppress("NOTHING_TO_INLINE")

package ink.bluecloud.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import tornadofx.*

inline fun Any.logger():Logger {
    return LoggerFactory.getLogger(javaClass)
}