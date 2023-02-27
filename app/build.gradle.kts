@file:Suppress("UnstableApiUsage")

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("kapt")
    alias(libs.plugins.android.appication)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlinx.kover)
}

android {
    namespace = "dev.shaarawy.githubtrends"
    compileSdk = libs.versions.app.sdk.compile.get().toInt()

    defaultConfig {
        applicationId = "dev.shaarawy.githubtrends"
        minSdk = libs.versions.app.sdk.min.get().toInt()
        targetSdk = libs.versions.app.sdk.target.get().toInt()
        versionCode = libs.versions.app.version.code.get().toInt()
        versionName = libs.versions.app.version.name.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    @Suppress("UnstableApiUsage")
    buildTypes {
        named("release") {
            isMinifyEnabled = true
            setProguardFiles(listOf("proguard-android-optimize.txt", "proguard-rules.pro"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(libs.kotlix.coroutines)
    implementation(libs.kotlix.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.logging.interceptor)
    implementation(libs.squareup.logcat)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.androidx.core)
    implementation(libs.androidx.appCompat)
    implementation(libs.google.android.material)
    implementation(libs.androidx.lifecycle)
    implementation(libs.bundles.compose)
    implementation(libs.google.dagger.hilt.android)
    testImplementation(libs.google.dagger.hilt.android.testing)
    kapt(libs.google.dagger.hilt.compiler)
    kaptTest(libs.google.dagger.hilt.compiler)
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.google.truth)
    testImplementation(libs.kotlix.coroutines.test)
    testImplementation(libs.squareup.okhttp3.mockwebserver)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso)
}