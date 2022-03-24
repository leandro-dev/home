package com.leandrodev.platforms

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler

@Suppress("UnstableApiUsage")
open class AndroidApplication : Plugin<Project> {

    override fun apply(target: Project) {
        configureKotlinAndroid(target)
        configureAndroidApplication(target)
        configureKotlinSerialization(target)
    }

    private fun configureKotlinAndroid(project: Project) {
        project.plugins.apply("org.jetbrains.kotlin.android")
    }

    private fun configureAndroidApplication(project: Project) {
        project.plugins.apply("com.android.application")
        project.plugins.apply("org.jetbrains.kotlin.kapt")
        with(project.dependencies) {
            applyDependencies("implementation", kotlinCommonDependencies)
            applyDependencies("implementation", androidDependencies)
            applyDependencies("testImplementation", kotlinCommonTestDependencies)
            applyDependencies("testImplementation", androidTestDependencies)
            applyDependencies("androidTestImplementation", androidDeviceTestDependencies)
            androidCustomConfigurations.forEach { (configuration, dependencies) ->
                applyDependencies(configuration.configurationName, dependencies)
            }
            androidPlatform.forEach {
                platform(it)
            }
        }
        project.extensions.configure(BaseAppModuleExtension::class.java) {
            setCompileSdkVersion(31)
            defaultConfig {
                minSdk = 23
                targetSdk = 31
                versionCode = 1
                versionName = "1.0"
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
            buildFeatures {
                compose = true

                // Disable unused AGP features
                aidl = false
                renderScript = false
                shaders = false
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
            composeOptions {
                kotlinCompilerExtensionVersion = Versions.Android.Jetpack.compose
            }
            testOptions.unitTests {
                isIncludeAndroidResources = true
            }
        }
    }

    private fun DependencyHandler.applyDependencies(
        configurationName: String,
        list: List<Dependency>,
    ) {
        list.forEach { dependency ->
            this.add(configurationName, dependency)
        }
    }


    private fun configureKotlinSerialization(project: Project) {
        project.plugins.apply("org.jetbrains.kotlin.plugin.serialization")
    }
}