/*
 * Copyright 2022-2023 RandX <010and1001@gmail.com>
 *
 * This file is part of Cutie.
 *
 * Cutie is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * Cutie is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with Cutie.
 * If not, see <https://www.gnu.org/licenses/>.
 *
 */

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    kotlin("native.cocoapods")
}

kotlin {
    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = libs.versions.cocoapods.name.get()
        }
    }

    cocoapods {
        version = libs.versions.cocoapods.version.get()
        summary = libs.versions.cocoapods.summary.get()
        homepage = libs.versions.cocoapods.homepage.get()
        name = libs.versions.cocoapods.name.get()
        ios.deploymentTarget = libs.versions.cocoapods.ios.deployment.target.get()
        framework {
            // Required properties
            // Framework name configuration. Use this property instead of deprecated 'frameworkName'
            baseName = libs.versions.cocoapods.name.get()
            // Optional properties
            // Dynamic framework support
            isStatic = false
        }
        podfile = project.file("../Cutie-iOS/Podfile")
    }

    sourceSets {
        //TODO for the Future
//        val commonMain by getting {
//            dependencies {
//
//            }
//        }
        //TODO for Testing
//        val commonMainTest by getting {
//            dependencies {
//                implementation(kotlin("test-common"))
//                implementation(kotlin("test-annotations-common"))
//            }
//        }
    }
}

android {
    compileSdk = libs.versions.target.sdk.get().toInt()
    sourceSets["main"].manifest.srcFile("src/AndroidManifest.xml")
    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        targetSdk = libs.versions.target.sdk.get().toInt()
    }
    namespace = libs.versions.namespace.get()
}
