buildscript {
    dependencies {
        classpath(libs.kotlin.serialization)
        classpath(libs.kotlin.gradle)
        classpath(libs.kover)
    }
}

// More about the DSL_SCOPE_VIOLATION https://youtrack.jetbrains.com/issue/KTIJ-19369
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.application") version libs.versions.android.gradle.plugin.get() apply false
    id("com.android.library") version libs.versions.android.gradle.plugin.get() apply false
    id("org.jetbrains.kotlin.android") version libs.versions.kotlin.get() apply false
    id("org.jetbrains.kotlin.plugin.serialization") version libs.versions.kotlin.get() apply false
    id("com.google.devtools.ksp") version libs.versions.ksp.get() apply false
    id("org.jetbrains.kotlinx.kover") version libs.versions.kover.get()
}

koverMerged {
    enable()
}


//Unfortunately this is a workaround to fix the following  compile error,   "Expecting an expresion"
//https://github.com/gradle/gradle/issues/20131
println("")