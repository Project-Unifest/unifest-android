import com.unifest.android.convention.applyPlugins
import com.unifest.android.convention.implementation
import com.unifest.android.convention.libs
import com.unifest.android.convention.project
import org.gradle.kotlin.dsl.dependencies

internal class AndroidFeatureConventionPlugin : BuildLogicConventionPlugin(
    {
        applyPlugins(
            "unifest.android.library",
            "unifest.android.library.compose",
            "unifest.android.hilt",
        )

        dependencies {
            implementation(project(path = ":core:data:api"))
            implementation(project(path = ":core:common"))
            implementation(project(path = ":core:designsystem"))
            implementation(project(path = ":core:model"))
            implementation(project(path = ":core:ui"))
            implementation(project(path = ":core:navigation"))

            implementation(libs.androidx.navigation.compose)
            implementation(libs.androidx.hilt.navigation.compose)
            implementation(libs.compose.effects)
            implementation(libs.bundles.androidx.lifecycle)
        }
    },
)
