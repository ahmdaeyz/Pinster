package dev.ahmdaeyz.pinster.ui.launcher.prefrerredcategories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.ahmdaeyz.pinster.domain.repositories.NewsSourcesRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class PreferredCategoriesViewModel(
        private val newsSourcesRepository: NewsSourcesRepository
) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val _getStarted = MutableLiveData<Boolean>()
    val categoriesSubject: PublishSubject<String> = PublishSubject.create()
    private val preferredCategories: MutableSet<String> = mutableSetOf()
    val getStarted: LiveData<Boolean>
    get() = _getStarted
    init {
        disposables.add(
                categoriesSubject.
                doOnNext { category->
                    if(category.contains("!")){
                        preferredCategories.remove(category.dropLast(1))
                    }else{
                        preferredCategories.add(category)
                    }
                }.
                subscribe()
        )
    }

    fun tappedGetStarted(){
        _getStarted.postValue(true)
        newsSourcesRepository.initializeSources(preferredCategories)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}