plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "1.9.0"
}

android {
    namespace = "com.mahdi.rostamipour.rpstore"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.mahdi.rostamipour.rpstore"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation("io.insert-koin:koin-android:3.5.0")
    implementation("io.insert-koin:koin-androidx-compose:3.5.0")

    implementation("io.ktor:ktor-client-core:2.3.4")
    implementation("io.ktor:ktor-client-okhttp:2.3.4")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.4")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    implementation("io.coil-kt:coil-compose:2.4.0")

    implementation("com.jakewharton.timber:timber:5.0.1")

    implementation("androidx.fragment:fragment-ktx:1.6.2")

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material3.adaptive.navigation.suite)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}