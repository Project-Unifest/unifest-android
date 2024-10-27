import com.android.build.gradle.LibraryExtension
import com.unifest.android.convention.Plugins
import com.unifest.android.convention.applyPlugins
import com.unifest.android.convention.configureAndroid
import com.unifest.android.convention.libs
import org.gradle.kotlin.dsl.configure

internal class AndroidLibraryConventionPlugin : BuildLogicConventionPlugin({
    applyPlugins(Plugins.ANDROID_LIBRARY, Plugins.KOTLIN_ANDROID)

    extensions.configure<LibraryExtension> {
        configureAndroid(this)

        defaultConfig.apply {
            targetSdk = libs.versions.targetSdk.get().toInt()
        }
    }
})
