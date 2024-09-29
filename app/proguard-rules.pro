# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

 -keep class com.unifest.android.feature.map.model.** { *; }

-dontwarn com.unifest.android.core.common.ErrorHandlerActions
-dontwarn com.unifest.android.core.common.HandleExceptionKt
-dontwarn com.unifest.android.core.common.ObserveEventKt
-dontwarn com.unifest.android.core.common.PermissionDialogButtonType
-dontwarn com.unifest.android.core.common.UiText$DirectString
-dontwarn com.unifest.android.core.common.UiText
-dontwarn com.unifest.android.core.common.extension.ContextKt
-dontwarn com.unifest.android.core.common.utils.DateUtilsKt
-dontwarn com.unifest.android.core.common.utils.DpToPxKt
-dontwarn com.unifest.android.core.data.datasource.RemoteConfigDataSource
-dontwarn com.unifest.android.core.data.datasource.RemoteConfigDataSourceImpl
-dontwarn com.unifest.android.core.data.di.FirebaseModule_ProvideMessagingFactory
-dontwarn com.unifest.android.core.data.di.FirebaseModule_ProvideRemoteConfigFactory
-dontwarn com.unifest.android.core.data.repository.BoothRepository
-dontwarn com.unifest.android.core.data.repository.BoothRepositoryImpl
-dontwarn com.unifest.android.core.data.repository.FestivalRepository
-dontwarn com.unifest.android.core.data.repository.FestivalRepositoryImpl
-dontwarn com.unifest.android.core.data.repository.LikedBoothRepository
-dontwarn com.unifest.android.core.data.repository.LikedBoothRepositoryImpl
-dontwarn com.unifest.android.core.data.repository.LikedFestivalRepository
-dontwarn com.unifest.android.core.data.repository.LikedFestivalRepositoryImpl
-dontwarn com.unifest.android.core.data.repository.MessagingRepository
-dontwarn com.unifest.android.core.data.repository.MessagingRepositoryImpl
-dontwarn com.unifest.android.core.data.repository.OnboardingRepository
-dontwarn com.unifest.android.core.data.repository.OnboardingRepositoryImpl
-dontwarn com.unifest.android.core.data.repository.RemoteConfigRepository
-dontwarn com.unifest.android.core.data.repository.RemoteConfigRepositoryImpl
-dontwarn com.unifest.android.core.data.repository.WaitingRepository
-dontwarn com.unifest.android.core.data.repository.WaitingRepositoryImpl
-dontwarn com.unifest.android.core.database.LikedBoothDao
-dontwarn com.unifest.android.core.database.LikedFestivalDao
-dontwarn com.unifest.android.core.database.di.DaoModule_ProvideLikedBoothDaoFactory
-dontwarn com.unifest.android.core.database.di.DaoModule_ProvideLikedFestivalDaoFactory
-dontwarn com.unifest.android.core.database.di.DatabaseModule_ProvideLikedBoothDatabaseFactory
-dontwarn com.unifest.android.core.database.di.DatabaseModule_ProvideLikedFestivalDatabaseFactory
-dontwarn com.unifest.android.core.datastore.OnboardingDataSource
-dontwarn com.unifest.android.core.datastore.OnboardingDataSourceImpl
-dontwarn com.unifest.android.core.datastore.RecentLikedFestivalDataSource
-dontwarn com.unifest.android.core.datastore.RecentLikedFestivalDataSourceImpl
-dontwarn com.unifest.android.core.datastore.TokenDataSource
-dontwarn com.unifest.android.core.datastore.TokenDataSourceImpl
-dontwarn com.unifest.android.core.datastore.di.DataStoreModule_ProvideOnboardingDataStore$datastore_releaseFactory
-dontwarn com.unifest.android.core.datastore.di.DataStoreModule_ProvideRecentFestivalDataStore$datastore_releaseFactory
-dontwarn com.unifest.android.core.designsystem.component.ButtonKt
-dontwarn com.unifest.android.core.designsystem.component.DialogKt
-dontwarn com.unifest.android.core.designsystem.component.LoadingWheelKt
-dontwarn com.unifest.android.core.designsystem.component.NetworkImageKt
-dontwarn com.unifest.android.core.designsystem.component.ScaffoldKt
-dontwarn com.unifest.android.core.designsystem.component.SearchTextFieldKt
-dontwarn com.unifest.android.core.designsystem.component.SnackBarKt
-dontwarn com.unifest.android.core.designsystem.theme.ColorKt
-dontwarn com.unifest.android.core.designsystem.theme.FontKt
-dontwarn com.unifest.android.core.designsystem.theme.ThemeKt
-dontwarn com.unifest.android.core.navigation.MainTabRoute$Home
-dontwarn com.unifest.android.core.navigation.MainTabRoute$Map
-dontwarn com.unifest.android.core.navigation.MainTabRoute$Menu
-dontwarn com.unifest.android.core.navigation.MainTabRoute$Stamp
-dontwarn com.unifest.android.core.navigation.MainTabRoute$Waiting
-dontwarn com.unifest.android.core.navigation.MainTabRoute
-dontwarn com.unifest.android.core.navigation.Route
-dontwarn com.unifest.android.core.network.di.ApiModule_ProvideUnifestService$network_releaseFactory
-dontwarn com.unifest.android.core.network.di.NetworkModule_ProvideHttpLoggingInterceptor$network_releaseFactory
-dontwarn com.unifest.android.core.network.di.NetworkModule_ProvideUnifestApiRetrofit$network_releaseFactory
-dontwarn com.unifest.android.core.network.di.NetworkModule_ProvideUnifestOkHttpClient$network_releaseFactory
-dontwarn com.unifest.android.core.network.service.UnifestService
-dontwarn com.unifest.android.core.ui.component.CameraPermissionTextProvider
-dontwarn com.unifest.android.core.ui.component.LikedFestivalGridKt
-dontwarn com.unifest.android.core.ui.component.PermissionDialogKt
-dontwarn com.unifest.android.core.ui.component.PermissionTextProvider
-dontwarn com.unifest.android.feature.booth.navigation.BoothNavigationKt
-dontwarn com.unifest.android.feature.booth.viewmodel.BoothViewModel
-dontwarn com.unifest.android.feature.booth.viewmodel.BoothViewModel_HiltModules$KeyModule
-dontwarn com.unifest.android.feature.festival.viewmodel.FestivalViewModel
-dontwarn com.unifest.android.feature.festival.viewmodel.FestivalViewModel_HiltModules$KeyModule
-dontwarn com.unifest.android.feature.home.navigation.HomeNavigationKt
-dontwarn com.unifest.android.feature.home.viewmodel.HomeViewModel
-dontwarn com.unifest.android.feature.home.viewmodel.HomeViewModel_HiltModules$KeyModule
-dontwarn com.unifest.android.feature.liked_booth.navigation.LikedBoothNavigationKt
-dontwarn com.unifest.android.feature.liked_booth.viewmodel.LikedBoothViewModel
-dontwarn com.unifest.android.feature.liked_booth.viewmodel.LikedBoothViewModel_HiltModules$KeyModule
-dontwarn com.unifest.android.feature.map.navigation.MapNavigationKt
-dontwarn com.unifest.android.feature.map.viewmodel.MapViewModel
-dontwarn com.unifest.android.feature.map.viewmodel.MapViewModel_HiltModules$KeyModule
-dontwarn com.unifest.android.feature.menu.navigation.MenuNavigationKt
-dontwarn com.unifest.android.feature.menu.viewmodel.MenuViewModel
-dontwarn com.unifest.android.feature.menu.viewmodel.MenuViewModel_HiltModules$KeyModule
-dontwarn com.unifest.android.feature.navigator.IntroNavigator
-dontwarn com.unifest.android.feature.navigator.MainNavigator
-dontwarn com.unifest.android.feature.navigator.Navigator$DefaultImpls
-dontwarn com.unifest.android.feature.navigator.Navigator
-dontwarn com.unifest.android.feature.waiting.navigation.WaitingNavigationKt
-dontwarn com.unifest.android.feature.waiting.viewmodel.WaitingViewModel
-dontwarn com.unifest.android.feature.waiting.viewmodel.WaitingViewModel_HiltModules$KeyModule
