//package com.twoup.personalfinance.repository
//
//import com.twoup.personalfinance.domain.model.category.CategoryResponseModel
//import com.twoup.personalfinance.domain.model.category.CategoryRequestModel
//import com.twoup.personalfinance.domain.model.wallet.getWallet.GetListWalletResponseModel
//import com.twoup.personalfinance.domain.repository.category.CategoryRepository
//import com.twoup.personalfinance.local.IDatabase
//import com.twoup.personalfinance.local.SecureStorageWrapper
//import com.twoup.personalfinance.mapping.mapToDomain
//import com.twoup.personalfinance.remote.services.category.CategoryDatSource
//import com.twoup.personalfinance.remote.services.category.CategoryDataSource
//import com.twoup.personalfinance.remote.services.category.CategoryService
//import com.twoup.personalfinance.remote.util.networkBoundResource
//import com.twoup.personalfinance.remote.util.safeApiCall
//import com.twoup.personalfinance.utils.data.map
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.flow.flowOf
//
//class CategoryRepositoryImpl(
//    private val dataSource : CategoryDatSource,
//    private val secureStorageWrapperImpl: SecureStorageWrapper,
//    private val categoryService : CategoryService
//    private val database: IDatabase
//
//) : CategoryRepository {
//
//    override suspend fun category(categoryRequest: CategoryRequestModel): Result<CategoryResponseModel> {
//        return safeApiCall { dataSource.category(categoryRequest) }.map { it.mapToDomain() }
//    }
//
//    fun getCategory(categoryRequest: CategoryRequestModel) = networkBoundResource(
//        query = {
//            flowOf(CategoryRequestModel(name = database.getAllLWallet()))
//
//        },
//        fetch = {
//            delay(2000)
//            categoryService.category(categoryRequest)
//        },
//        saveFetchResult = {},
//        shouldFetch = {category ->
//            category.isEmpty()
//        }
//    )
//}
//
////return networkBoundResource(
////query = {
////    flowOf(GetListWalletResponseModel(data = database.getAllLWallet()))
////},
////fetch = {
////    dataSource.getListWallets().map { it.mapToDomain()}
////},
////saveFetchResult = {fetchResult ->
////    database.clearDatabase()
////    fetchResult.data?.data?.forEach{
////        database.insertWallet(
////            id = it.id,
////            name = it.name,
////            amount = it.amount,
////            description = it.description,
////            walletGroup = it.walletGroup,
////        )
////    }
////}
////)
//
