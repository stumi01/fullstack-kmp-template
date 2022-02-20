import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlinx-serialization")
}

kotlin {
    android("android") {
        publishLibraryVariants("release")
    }
    jvm("jvm")
    ios {
        binaries {
            framework()
        }
    }

    js(IR) {
        useCommonJs()
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {

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
        named("jvmMain") {
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

