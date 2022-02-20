import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("com.squareup.sqldelight")
}

kotlin {
    jvm("desktop")
    android()

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget = when {
        System.getenv("SDK_NAME")?.startsWith("iphoneos") == true -> ::iosArm64
        System.getenv("NATIVE_ARCH")?.startsWith("arm") == true -> ::iosSimulatorArm64
        else -> ::iosX64
    }

    iosTarget("ios") {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }

    js(IR) {
        useCommonJs()
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")
                api(Deps.Koin.core)
                implementation(Deps.Kotlinx.coroutinesCore)
                api(Deps.Decompose.decompose)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        named("desktopMain") {
             dependencies {
             }
         }
        val iosMain by getting
        val iosTest by getting
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
sqldelight {
    database("TestDatabase") {
        packageName = "com.test.shared.db"
        schemaOutputDirectory = file("src/commonMain/sqldelight")
    }
}
