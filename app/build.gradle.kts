plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1"

}

android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.example.weatheradvisor"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.weatheradvisor"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "weatherApiKey", "\"${project.findProperty("weatherApiKey")}\"")
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.0-alpha03"
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
        buildConfig = true
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
    implementation (libs.androidx.fragment.ktx)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.appcompat)
    implementation(libs.volley)
    implementation(libs.play.services.location)
    implementation(libs.material)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.play.services.contextmanager)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.ui.viewbinding)
    implementation(libs.androidx.storage)
    implementation(libs.androidx.material3.v111)

    implementation (libs.androidx.constraintlayout.compose)

    kapt("androidx.room:room-compiler:2.5.0")

    // Retrofit dependencies
    implementation (libs.retrofit2.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    //Coil dependencies
    implementation(libs.coil.compose)

    debugImplementation(libs.androidx.ui.tooling)

    //google map dependencies
    implementation(libs.play.services.maps)
    implementation(libs.maps.compose.v220)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
