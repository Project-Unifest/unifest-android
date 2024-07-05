import com.unifest.android.Plugins
import com.unifest.android.applyPlugins
import com.unifest.android.implementation
import com.unifest.android.libs
import org.gradle.kotlin.dsl.dependencies

internal class AndroidRetrofitConventionPlugin : BuildLogicConventionPlugin(
    {
        applyPlugins(Plugins.KOTLINX_SERIALIZATION)

        dependencies {
            implementation(libs.retrofit)
            implementation(libs.retrofit.kotlinx.serialization.converter)
            implementation(libs.okhttp.logging.interceptor)
            implementation(libs.kotlinx.serialization.json)
        }
    },
)
