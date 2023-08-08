package pl.adrianczerwinski.settings

interface SettingsFeatureNavigation {
    fun openClientsList()
    fun openUserEdit()
    fun navigateUp()
    fun openClient(clientId: Int)
    fun openAddClient()
}
