import com.unifest.android.applyPlugins
import com.unifest.android.implementation
import com.unifest.android.libs
import com.unifest.android.project
import org.gradle.kotlin.dsl.dependencies

internal class AndroidFeatureConventionPlugin : BuildLogicConventionPlugin(
    {
        applyPlugins(
            "unifest.android.library",
            "unifest.android.library.compose",
            "unifest.android.hilt",
        )

        dependencies {
            implementation(project(path = ":core:common"))
            implementation(project(path = ":core:designsystem"))
            implementation(project(path = ":core:model"))
            implementation(project(path = ":core:ui"))
            implementation(project(path = ":feature:navigator"))

            implementation(libs.androidx.navigation.compose)
            implementation(libs.androidx.hilt.navigation.compose)
            implementation(libs.bundles.androidx.lifecycle)
        }
    },
)
