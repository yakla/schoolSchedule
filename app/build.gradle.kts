plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.main"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.main"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "Beta 0.81"

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
        sourceCompatibility = JavaVersion.VERSION_14
        targetCompatibility = JavaVersion.VERSION_14
    }
}

dependencies {
    implementation(libs.guava)
    implementation(libs.work.runtime)
    implementation (libs.jsoup)
//    implementation (libs.gson)
    implementation(libs.okhttp)
    implementation(libs.appcompat)
    implementation(libs.material)
//    implementation(libs.firebase.crashlytics.buildtools)
//    implementation (libs.play.services.ads)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}