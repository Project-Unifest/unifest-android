import com.android.build.gradle.LibraryExtension
import com.unifest.android.Plugins
import com.unifest.android.applyPlugins
import com.unifest.android.configureCompose
import org.gradle.kotlin.dsl.configure

internal class AndroidLibraryComposeConventionPlugin : BuildLogicConventionPlugin(
    {
        applyPlugins(Plugins.AndroidLibrary)

        extensions.configure<LibraryExtension> {
            configureCompose(this)
        }
    },
)


