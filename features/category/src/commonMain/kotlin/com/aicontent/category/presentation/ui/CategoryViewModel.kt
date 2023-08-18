package com.aicontent.category.presentation.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.twoup.personalfinance.domain.model.transaction.category.CategoryRequestModel
import com.twoup.personalfinance.domain.model.transaction.category.CategoryResponseModel
import com.twoup.personalfinance.domain.model.transaction.category.GetListCategoryResponseModel
import com.twoup.personalfinance.domain.model.wallet.getWallet.GetListWalletResponseModel
import com.twoup.personalfinance.domain.usecase.category.CategoryUseCase
import com.twoup.personalfinance.utils.data.Resource
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CategoryViewModel : ScreenModel, KoinComponent {
    private val categoryUseCase: CategoryUseCase by inject()

//    private val _categoryState = MutableStateFlow<Resource<CategoryResponseModel>>(Resource.loading())
//    val categoryState: StateFlow<Resource<CategoryResponseModel>> get() = _categoryState

    private val _categoryState = MutableStateFlow<Resource<GetListCategoryResponseModel>>(Resource.loading())
    val categoryState = _categoryState.asStateFlow()

    private val _categoryUiState = MutableStateFlow(CategoryUiState())
    val categoryUiState: StateFlow<CategoryUiState> = _categoryUiState.asStateFlow()

//    private val _createTransUiState = MutableStateFlow(CreateTransUiState())
//    val createTransUiState = _createTransUiState.asStateFlow()
//

//    private val _getListWalletState = MutableStateFlow<Resource<GetListWalletResponseModel>>(Resource.loading())
//    val getListWalletState = _getListWalletState.asStateFlow()


    init {
        getListCategory()
    }
    @OptIn(DelicateCoroutinesApi::class)
    private fun getListCategory() {
        GlobalScope.launch {
            categoryUseCase.invokeCategory().collect(){
                _categoryState.emit(it)
            }
//            _getListWalletState.tryEmit(getListWalletsUseCase().first())
        }
    }

     fun createCategory() {

        _categoryUiState.value =
            categoryUiState.value.copy(
                isLoading = true
            )

        // Validate Category ID
        if (categoryUiState.value.categoryId.isEmpty()) {
            _categoryUiState.value = categoryUiState.value.copy(
                isLoading = false,
                error = "Category ID is required."
            )
            return
        }

        coroutineScope.launch {
            delay(200)

//            categoryUseCase.invokeCategory(
////                CategoryRequestModel(
////                    name = categoryUiState.value.name,
////                    category = categoryUiState.value.categoryId
////                )
//            )

            val categoryResponse = categoryUseCase(
                CategoryRequestModel(
                    name = categoryUiState.value.name,
                    category = categoryUiState.value.categoryId
                )
            )

            if (categoryResponse.isSuccessful()) {
                _categoryState.tryEmit(Resource.success(GetListCategoryResponseModel()))
            } else {
//                if (categoryResponse.isError()) {
//                    _categoryState.tryEmit(Resource.error(CategoryInfo()))
//                }
            }
        }
    }

    fun retrieveAccessToken() {
        coroutineScope.launch {
            // Perform the access token retrieval logic here
            // For example, make an API call using a repository or data source

            val accessToken = "your_access_token_here"

            // Update the UI state with the retrieved access token
            _categoryUiState.value = categoryUiState.value.copy(accessToken = accessToken)
        }
    }

//    private fun handleErrorResponse() {
//        // Handle the error scenario
//        _categoryUiState.value = categoryUiState.value.copy(
//            isLoading = false,
//            error = "Error occurred during category creation."
//        )
//    }

    fun onNameValueChange(text: String) {
        _categoryUiState.value = categoryUiState.value.copy(name = text)
    }

    fun onCategoryIdValueChange(text: String) {
        _categoryUiState.value = categoryUiState.value.copy(categoryId = text)
    }
}
