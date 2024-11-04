plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    kotlin("kapt")
}

android {
    namespace = "com.codecrafters.dishdelight"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.codecrafters.dishdelight"
        minSdk = 24
        targetSdk = 35
        versionCode = 2
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    implementation("com.google.firebase:firebase-firestore-ktx:25.1.0")
    implementation("com.google.firebase:firebase-messaging:24.0.3")
    implementation("androidx.biometric:biometric-ktx:1.1.0")
    implementation("com.google.firebase:firebase-messaging-ktx:24.0.3")
    implementation("androidx.room:room-common:2.6.1")
    implementation("androidx.test:core-ktx:1.6.1")
    kapt ("com.github.bumptech.glide:compiler:4.15.1")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-bom:33.3.0")
    implementation ("com.google.android.gms:play-services-auth:20.5.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(platform("com.google.firebase:firebase-bom:33.3.0"))
    implementation("com.google.firebase:firebase-auth:23.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation ("com.google.code.gson:gson:2.8.9")
    implementation ("androidx.biometric:biometric-ktx:1.4.0-alpha02")
    implementation ("androidx.room:room-runtime:2.5.0")
    kapt ("androidx.room:room-compiler:2.5.0")
    implementation ("androidx.work:work-runtime-ktx:2.7.1")
    implementation ("androidx.room:room-ktx:2.4.3")
    testImplementation ("org.mockito:mockito-core:3.9.0")
    testImplementation ("org.robolectric:robolectric:4.9.1")
}