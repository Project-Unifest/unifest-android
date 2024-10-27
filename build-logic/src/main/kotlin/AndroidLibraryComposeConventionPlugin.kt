import com.android.build.gradle.LibraryExtension
import com.unifest.android.convention.Plugins
import com.unifest.android.convention.applyPlugins
import com.unifest.android.convention.configureCompose
import org.gradle.kotlin.dsl.configure

internal class AndroidLibraryComposeConventionPlugin : BuildLogicConventionPlugin(
    {
        applyPlugins(Plugins.ANDROID_LIBRARY, Plugins.KOTLIN_COMPOSE)
        // applyPlugins(Plugins.ANDROID_LIBRARY)

        extensions.configure<LibraryExtension> {
            configureCompose(this)
        }
    },
)


