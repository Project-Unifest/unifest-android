import com.android.build.api.dsl.ApplicationExtension
import com.unifest.android.Plugins
import com.unifest.android.applyPlugins
import com.unifest.android.configureCompose
import org.gradle.kotlin.dsl.configure

internal class AndroidApplicationComposeConventionPlugin : BuildLogicConventionPlugin(
    {
        applyPlugins(Plugins.ANDROID_APPLICATION, Plugins.COMPOSE_COMPILER)

        extensions.configure<ApplicationExtension> {
            configureCompose(this)
        }
    },
)
