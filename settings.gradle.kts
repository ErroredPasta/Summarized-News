pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Summarized News"
include(":app")
include(":core:core_ui")
include(":core:core_util")
include(":news:news_ui")
include(":news:news_domain")
include(":summary:summary_domain")
include(":news:news_data")
include(":summary:summary_data")
