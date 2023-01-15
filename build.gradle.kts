import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.openjfx.javafxplugin")
    id("com.github.johnrengelman.shadow")
}

group = "ink.bluecloud"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    maven ("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
    maven ("https://repo.huaweicloud.com/repository/maven/")
    maven ("https://maven.aliyun.com/repository/central/")
    maven ("https://maven.aliyun.com/repository/public/")
    mavenCentral()
}

javafx {
    version = "19"
    modules = listOf("javafx.controls", "javafx.media")
}

val zxingVersion:String by properties
val koinVersion:String by properties
val koinLoggerVersion:String by properties
val koinKspVersion:String by properties
val okhttpVersion:String by properties
val coroutineVersion:String by properties
val kotlinVersion:String by properties
val ztExecVersion:String by properties
val bouncycastleVersion:String by properties
val jsoupVersion:String by properties
val log4jVersion:String by properties
val fastjsonVersion:String by properties
val protobufVersion:String by properties
val h2Version:String by properties
val ktormCoreVersion:String by properties
val ktormKspVersion:String by properties

dependencies {
    //GUI
    implementation(files(
        "libs\\ElementFX.jar",
        "libs\\CloudTools.jar",
        "libs\\KotlinFx.jar"
    ))
    implementation(group = "io.github.palexdev", name = "materialfx", version = "11.13.8")

    //Kotlin
    implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-javafx", version = coroutineVersion)
    implementation(group = "org.jetbrains.kotlin", name = "kotlin-reflect", version = kotlinVersion)

    //koin
    implementation("io.insert-koin:koin-core:${koinVersion}")
    implementation("io.insert-koin:koin-logger-slf4j:${koinLoggerVersion}")
    implementation("io.insert-koin:koin-annotations:${koinKspVersion}")
    ksp("io.insert-koin:koin-ksp-compiler:${koinKspVersion}")

    //Utils
    implementation(group = "org.zeroturnaround", name = "zt-exec", version = ztExecVersion)

    //DataBase
//    implementation(group = "com.h2database", name = "h2", version = h2Version)
//    implementation(group = "org.ktorm", name = "ktorm-core", version = ktormCoreVersion)
//    implementation(group = "org.ktorm", name = "ktorm-ksp-api", version = ktormKspVersion)
//    ksp(group = "org.ktorm", name = "ktorm-ksp-compiler", version = ktormKspVersion)

    //Http
    implementation(group = "com.squareup.okhttp3", name = "okhttp", version = okhttpVersion)
    implementation(group = "org.jsoup", name = "jsoup", version = jsoupVersion)
    implementation(group = "org.bouncycastle", name = "bcprov-jdk15on", version = bouncycastleVersion)

    //Serialization
    implementation(group = "com.alibaba.fastjson2", name = "fastjson2-kotlin", version = fastjsonVersion)
    implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-serialization-protobuf-jvm", version = protobufVersion)

    //QRCode
    implementation(group = "com.google.zxing", name = "core", version = zxingVersion)
    implementation(group = "com.google.zxing", name = "javase", version = zxingVersion)

    //Logger
    implementation(group = "org.apache.logging.log4j", name = "log4j-core", version = log4jVersion)
    implementation(group = "org.apache.logging.log4j", name = "log4j-api", version = log4jVersion)
    implementation(group = "org.apache.logging.log4j", name = "log4j-slf4j-impl", version = log4jVersion)

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
}

sourceSets.main {
    java.srcDirs("build/generated/ksp/main/kotlin")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.run {
        jvmTarget = "17"
//        freeCompilerArgs += "-Xlambdas=indy"
        freeCompilerArgs += "-Xcontext-receivers"
    }
}

application {
    mainClass.set("ink.bluecloud.MainKt")
}

/*
running vm options on hotspot
--add-opens=javafx.controls/javafx.scene.control.skin=ALL-UNNAMED
--add-opens=javafx.media/javafx.scene.media=ALL-UNNAMED
--add-opens=javafx.media/com.sun.media.jfxmedia.locator=ALL-UNNAMED
*/