import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.7.21"
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("com.github.johnrengelman.shadow") version "7.1.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.21"
    id("com.google.devtools.ksp") version "1.7.21-1.0.8"
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

dependencies {
    //GUI
    implementation(files("libs\\ElementFX.jar"))
    implementation(files("libs\\CloudTools.jar"))
    implementation(files("libs\\KotlinFx.jar"))

    //Kotlin
    implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-javafx", version = "1.6.4")
    implementation(group = "org.jetbrains.kotlin", name = "kotlin-reflect", version = "1.7.21")

    //koin
    implementation("io.insert-koin:koin-core:3.2.2")
    implementation("io.insert-koin:koin-annotations:1.0.3")
    implementation("io.insert-koin:koin-logger-slf4j:3.2.2")
    ksp("io.insert-koin:koin-ksp-compiler:1.0.3")

    //Utils
    implementation(group = "org.zeroturnaround", name = "zt-exec", version = "1.12")

    //DataBase
    implementation(group = "org.ktorm", name = "ktorm-core", version = "3.5.0")
    implementation(group = "org.ktorm", name = "ktorm-ksp-api", version = "1.0.0-RC3")
    implementation(group = "com.h2database", name = "h2", version = "2.1.214")
    ksp(group = "org.ktorm", name = "ktorm-ksp-compiler", version = "1.0.0-RC3")

    //Http
    implementation(group = "com.squareup.okhttp3", name = "okhttp", version = "5.0.0-alpha.10")
    implementation(group = "org.jsoup", name = "jsoup", version = "1.15.3")

    //Serialization
    implementation(group = "com.alibaba.fastjson2", name = "fastjson2-kotlin", version = "2.0.19")
    implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-serialization-protobuf-jvm", version = "1.4.1")

    //QRCode
    implementation(group = "com.google.zxing", name = "core", version = "3.5.1")
    implementation(group = "com.google.zxing", name = "javase", version = "3.5.1")

    //Logger
    implementation(group = "org.apache.logging.log4j", name = "log4j-core", version = "2.19.0")
    implementation(group = "org.apache.logging.log4j", name = "log4j-api", version = "2.19.0")
    implementation(group = "org.apache.logging.log4j", name = "log4j-slf4j-impl", version = "2.19.0")
//    implementation(group = "io.github.microutils", name = "kotlin-logging-jvm", version = "3.0.2")

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
//        this.freeCompilerArgs += "-Xlambdas=indy"

    }
}

application {
    mainClass.set("ink.bluecloud.MainKt")
}