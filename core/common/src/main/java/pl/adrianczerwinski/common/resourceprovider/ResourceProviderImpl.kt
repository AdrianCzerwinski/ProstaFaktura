package pl.adrianczerwinski.common.resourceprovider

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class ResourceProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ResourceProvider {
    override fun getString(stringResId: Int): String = context.getString(stringResId)
}
