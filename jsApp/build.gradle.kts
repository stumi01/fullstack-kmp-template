plugins {
    kotlin("multiplatform")
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                optIn("kotlinx.serialization.InternalSerializationApi")
                optIn("kotlin.time.ExperimentalTime")
                optIn("org.jetbrains.compose.common.ui.ExperimentalComposeWebWidgetsApi")
            }
        }
        val jsMain by getting {
            dependencies {
                implementation("dev.fritz2:core:0.12")
                implementation("dev.fritz2:components:0.12")
                implementation(project(":shared"))
            }
        }
    }
}

rootProject.plugins.withType(org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin::class.java) {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>().versions.webpackCli.version = "4.9.0"
}
