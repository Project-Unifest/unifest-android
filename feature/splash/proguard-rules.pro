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

-dontwarn com.unifest.android.core.common.ObserveEventKt
-dontwarn com.unifest.android.core.common.extension.ContextKt
-dontwarn com.unifest.android.core.designsystem.component.DialogKt
-dontwarn com.unifest.android.core.designsystem.component.LoadingWheelKt
-dontwarn com.unifest.android.core.designsystem.theme.ColorKt
-dontwarn com.unifest.android.core.designsystem.theme.ThemeKt
-dontwarn com.unifest.android.feature.navigator.IntroNavigator
-dontwarn com.unifest.android.feature.navigator.MainNavigator
-dontwarn com.unifest.android.feature.navigator.Navigator$DefaultImpls
-dontwarn com.unifest.android.feature.navigator.Navigator
