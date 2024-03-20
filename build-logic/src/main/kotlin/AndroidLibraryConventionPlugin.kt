import com.android.build.gradle.LibraryExtension
import com.unifest.android.ApplicationConfig
import com.unifest.android.Plugins
import com.unifest.android.applyPlugins
import com.unifest.android.configureAndroid
import org.gradle.kotlin.dsl.configure

internal class AndroidLibraryConventionPlugin : BuildLogicConventionPlugin({
    applyPlugins(Plugins.AndroidLibrary, Plugins.KotlinAndroid)

    extensions.configure<LibraryExtension> {
        configureAndroid(this)

        defaultConfig.apply {
            targetSdk = ApplicationConfig.TargetSdk
        }
    }
})
