import org.jetbrains.compose.compose

plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose")
}

dependencies {
    implementation(project(":sharedCompose"))
    implementation(compose.material)
    implementation(Deps.AndroidX.activityCompose)
    implementation(Deps.AndroidX.appcompat)
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "com.bencestumpf.template.android"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        resources.excludes.add("META-INF/*")
    }
}
