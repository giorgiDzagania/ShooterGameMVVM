plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.madeit.shooters"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.madeit.shooters"
        minSdk = 28
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.3.0")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.13.2")

    // Circular Image
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Lottie Animated Button
    implementation("com.airbnb.android:lottie:6.0.0")

    // ViewModel - Now We Can Use ViewModelScope
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    // Delegates gives ability to create viewModel in correct way in Activity
    implementation("androidx.activity:activity-ktx:1.9.3")

}