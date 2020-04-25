package dev.ahmdaeyz.pinster.ui.launcher.prefrerredcategories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.ahmdaeyz.pinster.domain.repositories.NewsSourcesRepository

@Suppress("UNCHECKED_CAST")
class PreferredCategoriesViewModelFactory(
        private val newsSourcesRepository: NewsSourcesRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PreferredCategoriesViewModel(newsSourcesRepository) as T
    }
}