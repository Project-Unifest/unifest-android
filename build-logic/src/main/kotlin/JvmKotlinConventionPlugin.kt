import com.unifest.android.convention.Plugins
import com.unifest.android.convention.ApplicationConfig
import com.unifest.android.convention.applyPlugins
import com.unifest.android.convention.detektPlugins
import com.unifest.android.convention.libs
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

internal class JvmKotlinConventionPlugin : BuildLogicConventionPlugin({
    applyPlugins(Plugins.JAVA_LIBRARY, Plugins.KOTLIN_JVM)

    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = ApplicationConfig.JavaVersion
        targetCompatibility = ApplicationConfig.JavaVersion
    }

    extensions.configure<KotlinProjectExtension> {
        jvmToolchain(ApplicationConfig.JavaVersionAsInt)
    }

    dependencies {
        detektPlugins(libs.detekt.formatting)
    }
})
