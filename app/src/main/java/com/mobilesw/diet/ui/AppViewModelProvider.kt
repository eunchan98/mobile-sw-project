package com.mobilesw.diet.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.mobilesw.diet.AppContainer
import com.mobilesw.diet.DietApplication

/**
 * ViewModel 팩토리에서 저장소를 꺼낼 때 쓴다.
 *
 * 사용 예:
 * ```
 * val vm: HomeViewModel = viewModel(factory = viewModelFactory {
 *     initializer { HomeViewModel(appContainer().mealRepository) }
 * })
 * ```
 */
fun CreationExtras.appContainer(): AppContainer =
    (this[APPLICATION_KEY] as DietApplication).container
