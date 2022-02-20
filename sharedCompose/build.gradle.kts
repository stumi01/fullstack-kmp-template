import org.jetbrains.compose.compose
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("kotlin-parcelize")
}

kotlin {
    jvm("desktop")
    android()

    js(IR) {
        useCommonJs()
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                api(Deps.Decompose.decompose)
                api(Deps.Decompose.extensionsCompose)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                api(Deps.Decompose.decompose)
                api(Deps.Decompose.extensionsCompose)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        named("desktopMain") {
             dependencies {
                 api(Deps.Decompose.decompose)
                 api(Deps.Decompose.extensionsCompose)
             }
         }
        val jsMain by getting {
            dependencies {
                api(Deps.Decompose.decompose)
                api(Deps.Decompose.extensionsCompose)
            }
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }
}
