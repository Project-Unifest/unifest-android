# unifest-android
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.23-blue.svg)](https://kotlinlang.org)
[![Gradle](https://img.shields.io/badge/gradle-8.7-green.svg)](https://gradle.org/)
[![Android Studio](https://img.shields.io/badge/Android%20Studio-2023.2.1%20%28Iguana%29-green)](https://developer.android.com/studio)
[![minSdkVersion](https://img.shields.io/badge/minSdkVersion-26-red)](https://developer.android.com/distribute/best-practices/develop/target-sdk)
[![targetSdkVersion](https://img.shields.io/badge/targetSdkVersion-34-orange)](https://developer.android.com/distribute/best-practices/develop/target-sdk)
<br/>

유니페스 : 대학 축제 지도를 펼쳐라! [PlayStore](https://play.google.com/store/apps/details?id=com.unifest.android)
![unifest_graphic_image](https://github.com/Project-Unifest/unifest-android/assets/51016231/f81c1776-67b4-4d95-895b-4f7c404c3198)

<p align="center">
<img src="https://github.com/Project-Unifest/unifest-android/assets/51016231/4d73d229-fdae-4b43-9499-dfb366085820" width="30%"/>
<img src="https://github.com/Project-Unifest/unifest-android/assets/51016231/44cb2818-a2da-458b-8e18-eef7918a2b33" width="30%"/>
<img src="https://github.com/Project-Unifest/unifest-android/assets/51016231/1573f2b8-7194-4e08-b22e-029388fe5ebb" width="30%"/>
</p>
<p align="center">
<img src="https://github.com/Project-Unifest/unifest-android/assets/51016231/b371c270-9986-4bf4-8b06-3428b44243ea" width="30%"/>
<img src="https://github.com/Project-Unifest/unifest-android/assets/51016231/a2e1ce99-b9e0-4423-a46d-261bf2d4bb2e" width="30%"/>
<img src="https://github.com/Project-Unifest/unifest-android/assets/51016231/be979801-9533-4db2-83ef-1fd33301cc0c" width="30%"/>
</p>

## Features

## Development

### Required

- IDE : Android Studio Iguana
- JDK : Java 17을 실행할 수 있는 JDK
- Kotlin Language : 1.9.23

### Language

- Kotlin

### Libraries

- AndroidX
  - Activity Compose
  - Core
  - Lifecycle & ViewModel Compose
  - Navigation
  - DataStore
  - Room
  - StartUp
  - Splash

- Kotlin Libraries (Coroutine, Serialization, Immutable Collection)
- Compose
  - Material3
  - Navigation

- Dagger Hilt
- Retrofit, OkHttp
- Timber
- [Compose-Stable-Marker](https://github.com/skydoves/compose-stable-marker)
- [Landscapist](https://github.com/skydoves/landscapist), Coil-Compose
- [ComposeExtensions](https://github.com/taehwandev/ComposeExtensions)
- [ComposeInvestigator](https://github.com/jisungbin/ComposeInvestigator)
- [Naver-Map-Compose](https://github.com/fornewid/naver-map-compose)
- [Balloon](https://github.com/skydoves/Balloon)
- [Calendar](https://github.com/kizitonwose/Calendar)
- Firebase(Analytics, Crashlytics)

#### Test & Code analysis

- Ktlint
- Detekt

#### Gradle Dependency

- Gradle Version Catalog

## Architecture
Google Recommend Architecture based on [Now in Android](https://github.com/android/nowinandroid)

## Module
<img width="1309" alt="image" src="https://github.com/Project-Unifest/unifest-android/assets/51016231/c195f537-bc3f-42ba-98ca-d0ffaacb01ae">

## Package Structure
```
├── app
│   └── application
├── build-logic
├── core
│   ├── common
│   ├── data
│   ├── database
│   ├── datastore
│   ├── designsystem
│   ├── model
│   ├── network
│   └── ui
├── feature
│   ├── booth
│   ├── home
│   ├── intro
│   ├── liked-booth
│   ├── main 
│   ├── map
│   ├── menu
│   ├── navigator
│   ├── splash
│   └── waiting
├── gradle
│   └── libs.versions.toml
└── report
    ├── compose-metrics
    └── compose-reports
```
<br/>
