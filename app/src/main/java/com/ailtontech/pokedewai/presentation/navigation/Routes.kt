package com.ailtontech.pokedewai.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.ailtontech.pokedewai.R // Assuming R file will be generated/available
import kotlinx.serialization.Contextual // Uncommented
import kotlinx.serialization.Serializable // Required for @Serializable

/**
 * Base sealed interface for all navigation routes in the application.
 * All routes must be serializable.
 */
@Serializable
sealed interface Route

/**
 * Defines the primary navigation routes within the application.
 * Each NavRoute is serializable and provides its own route path.
 *
 * @param label The string resource ID for the route's label.
 * @param selectedIcon The icon to display when the route is selected.
 * @param unselectedIcon The icon to display when the route is not selected.
 */
@Serializable
sealed class NavRoutes(
    @StringRes val label: Int,
    @Contextual val selectedIcon: ImageVector, // Added @Contextual
    @Contextual val unselectedIcon: ImageVector // Added @Contextual
) : Route {

    @Serializable
    data object Home : NavRoutes(
        label = R.string.nav_home_label, // Placeholder
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )

    @Serializable
    data object Search : NavRoutes(
        label = R.string.nav_search_label, // Placeholder
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search
    )

    @Serializable
    data object Locations : NavRoutes(
        label = R.string.nav_locations_label, // Placeholder
        selectedIcon = Icons.Filled.LocationOn,
        unselectedIcon = Icons.Outlined.LocationOn
    )

    @Serializable
    data object Settings : NavRoutes(
        label = R.string.nav_settings_label, // Placeholder
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings
    )

    companion object {
        val allNavRoutes: List<NavRoutes> = listOf(
            Home,
            Search,
            Locations,
            Settings
        )
    }
}
