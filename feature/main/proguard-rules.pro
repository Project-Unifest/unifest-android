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

-dontwarn com.unifest.android.core.common.UiText
-dontwarn com.unifest.android.core.designsystem.component.ScaffoldKt
-dontwarn com.unifest.android.core.designsystem.component.SnackBarKt
-dontwarn com.unifest.android.core.designsystem.theme.ColorKt
-dontwarn com.unifest.android.core.designsystem.theme.FontKt
-dontwarn com.unifest.android.core.designsystem.theme.ThemeKt
-dontwarn com.unifest.android.core.navigation.MainTabRoute$Home
-dontwarn com.unifest.android.core.navigation.MainTabRoute$Map
-dontwarn com.unifest.android.core.navigation.MainTabRoute$Menu
-dontwarn com.unifest.android.core.navigation.MainTabRoute$Waiting
-dontwarn com.unifest.android.core.navigation.MainTabRoute
-dontwarn com.unifest.android.core.navigation.Route
-dontwarn com.unifest.android.feature.booth.navigation.BoothNavigationKt
-dontwarn com.unifest.android.feature.home.navigation.HomeNavigationKt
-dontwarn com.unifest.android.feature.liked_booth.navigation.LikedBoothNavigationKt
-dontwarn com.unifest.android.feature.map.navigation.MapNavigationKt
-dontwarn com.unifest.android.feature.menu.navigation.MenuNavigationKt
-dontwarn com.unifest.android.feature.stamp.navigation.StampNavigationKt
-dontwarn com.unifest.android.feature.waiting.navigation.WaitingNavigationKt
-dontwarn java.lang.invoke.StringConcatFactory
