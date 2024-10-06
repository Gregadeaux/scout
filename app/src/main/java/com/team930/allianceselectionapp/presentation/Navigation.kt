package com.team930.allianceselectionapp.presentation

enum class Page {
    DASHBOARD,
    ALLIANCE_SELECTION,
    MATCH_PREVIEW,
    DATA_TABLES,
    PICK_LIST_EDITOR,
    PIT_SCOUTING,
    MATCH_SCOUTING,
    SETTINGS
}

sealed class NavigationItem(
    val route: String,
    val title: String,
    val page: Page
) {
    data object Dashboard : NavigationItem("dashboard", "Dashboard", Page.DASHBOARD)
    data object AllianceSelection : NavigationItem("alliance_selection", "Alliance Selection", Page.ALLIANCE_SELECTION)
    data object MatchPreview : NavigationItem("match_preview", "Match Preview", Page.MATCH_PREVIEW)
    data object DataTables : NavigationItem("data_tables", "Data Tables", Page.DATA_TABLES)
    data object PickListEditor : NavigationItem("pick_list_editor", "Pick List Editor", Page.PICK_LIST_EDITOR)
    data object PitScouting : NavigationItem("pit_scouting", "Pit Scouting", Page.PIT_SCOUTING)
    data object MatchScouting : NavigationItem("match_scouting", "Match Scouting", Page.MATCH_SCOUTING)
    data object Settings : NavigationItem("settings", "Settings", Page.SETTINGS)
}