import com.android.build.gradle.LibraryExtension
import com.unifest.android.Plugins
import com.unifest.android.applyPlugins
import com.unifest.android.configureAndroid
import com.unifest.android.libs
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
