import com.unifest.android.Plugins
import com.unifest.android.applyPlugins
import com.unifest.android.implementation
import com.unifest.android.ksp
import com.unifest.android.libs
import org.gradle.kotlin.dsl.dependencies

internal class AndroidHiltConventionPlugin : BuildLogicConventionPlugin(
    {
        applyPlugins(Plugins.HILT, Plugins.KSP)

        dependencies {
            implementation(libs.hilt.android)
            ksp(libs.hilt.android.compiler)
        }
    },
)
