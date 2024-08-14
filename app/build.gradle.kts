plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.zonassoft.footprintforlife"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.zonassoft.footprintforlife"
        minSdk = 21
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    
    implementation(libs.gson)
    implementation(libs.material.ripple)
    implementation(libs.materialdatetimepicker)
    implementation(libs.circularimageview)
    implementation(libs.glide)
    implementation(libs.shimmer)
    implementation(libs.runtime)
    annotationProcessor(libs.compiler)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.flexbox)
    implementation(libs.materialchipsInput)
    implementation(libs.nachos)
    implementation(libs.crystalrangeseekbar)
    implementation(libs.mpandroidchart)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)


}