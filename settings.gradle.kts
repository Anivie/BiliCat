rootProject.name = "BiliCat"

pluginManagement {
    val kotlinVersion: String by settings
    val javafxPluginVersion: String by settings
    val kspPluginVersion: String by settings
    val shadowVersion: String by settings

    plugins {
        kotlin("jvm")version kotlinVersion
        id("org.jetbrains.kotlin.plugin.serialization")version kotlinVersion
        id("com.google.devtools.ksp")version "${kotlinVersion}-${kspPluginVersion}"
        id("org.openjfx.javafxplugin")version javafxPluginVersion
        id("com.github.johnrengelman.shadow")version shadowVersion
    }
}