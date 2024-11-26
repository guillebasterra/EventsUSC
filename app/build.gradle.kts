plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
}

android {
    namespace = "com.example.eventsusc"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.eventsusc"
        minSdk = 24
        targetSdk = 34
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // Firebase dependencies
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation("com.google.android.gms:play-services-auth:20.5.0")

    // Google Maps dependency
    implementation("com.google.android.gms:play-services-maps:18.0.2")

    // RecyclerView dependency
    implementation("androidx.recyclerview:recyclerview:1.2.1")

    // Unit test dependencies
    testImplementation(libs.junit)
    testImplementation("org.robolectric:robolectric:4.10.3")
    testImplementation("androidx.test:core:1.5.0")
    testImplementation("androidx.test.ext:junit:1.1.5")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:3.2.0")
    // Instrumented test dependencies
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // Add this
    androidTestImplementation(libs.espresso.core)

    androidTestImplementation("androidx.test.espresso:espresso-intents:3.6.1")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.0")

}