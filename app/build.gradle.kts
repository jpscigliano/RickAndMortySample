plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.kotlinx.kover")
}

android {
    namespace = "com.sample.rickandmorty"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.sample.rickandmorty"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
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
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    koverMerged {
        enable()
    }

    testOptions {
        unitTests.all {
            if (it.name == "testDebugUnitTest") {
                it.extensions.configure(kotlinx.kover.api.KoverTaskExtension::class) {
                    // set to true to disable instrumentation of this task,
                    // Kover reports will not depend on the results of its execution
                    isDisabled.set(false)

                    // set file name of binary report
                    reportFile.set(file("$buildDir/custom/debug-report.bin"))

                }
            }
        }
    }

}

dependencies {

    implementation(project(":coreX:coreXDomain"))

    implementation(project(":feedData"))
    implementation(project(":feedDomain"))
    implementation(project(":feedFramework"))
    implementation(project(":feedPresentation"))


    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime)
    implementation(libs.compose.activity)
    implementation(libs.compose.ui)
    implementation(libs.compose.tooling)
    implementation(libs.compose.animation)
    implementation(libs.compose.material3)
    implementation(libs.compose.material3.window)

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.navigation.runtime)
    implementation(libs.accompanist.navigation)

    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.androidx.extensions)
    debugImplementation(libs.compose.tooling)
    debugImplementation(libs.compose.ui.test)
}
