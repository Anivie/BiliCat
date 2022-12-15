rootProject.name = "BiliCat"

pluginManagement {
    val kotlinVersion: String by settings
    val javafxVersion: String by settings
    val shadowVersion: String by settings

    plugins {
        kotlin("jvm")version kotlinVersion
        id("org.jetbrains.kotlin.plugin.serialization")version kotlinVersion
        id("com.google.devtools.ksp")version "${kotlinVersion}-1.0.8"
        id("org.openjfx.javafxplugin")version javafxVersion
        id("com.github.johnrengelman.shadow")version shadowVersion
    }
}