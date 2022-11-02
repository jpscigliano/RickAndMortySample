plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.devtools.ksp")
    id("kotlinx-serialization")
    id("org.jetbrains.kotlinx.kover")

}

android {
    namespace = "com.sample.rickandmorty"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

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
    packagingOptions {
        resources.excludes.add("META-INF/*")
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(":coreX:coreXDomain"))
    implementation(project(":coreX:coreXData"))
    implementation(project(":coreX:coreXFramework"))

    implementation(project(":feedData"))
    implementation(project(":feedDomain"))

    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization)

    implementation(libs.ktor.core)
    implementation(libs.ktor.logging)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.contentNegotiation)
    implementation(libs.ktor.android)

    implementation(libs.koin.core)
    implementation(libs.koin.android)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    ksp(libs.room.compiler)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.room.test)
    testImplementation(libs.junit.androidx.extensions)

    testImplementation(libs.roboelectric)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.androidx.test.core.arch)
    testImplementation(libs.ktor.test)

}
