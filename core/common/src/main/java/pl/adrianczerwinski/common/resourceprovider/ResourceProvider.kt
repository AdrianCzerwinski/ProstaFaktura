package pl.adrianczerwinski.common.resourceprovider

interface ResourceProvider {
    fun getString(stringResId: Int): String
}
