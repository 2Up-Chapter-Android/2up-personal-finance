package com.twoup.personalfinance.domain.repository.transaction

import com.twoup.personalfinance.domain.model.transaction.account.AccountLocalModel
import com.twoup.personalfinance.domain.model.transaction.category.CategoryLocalModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.model.transaction.note.NoteTransactionEntity

interface TransactionLocalDataSource {
//    suspend fun insertSubCategory(subCategories: SubCategoriesLocalModel)
    suspend fun insertCategoryIncome(category: CategoryLocalModel)
    suspend fun insertCategoryExpenses(category: CategoryLocalModel)
    suspend fun insertAccount(account : AccountLocalModel)
    suspend fun insertTransaction(transaction : TransactionLocalModel)
    suspend fun insertNote(note: NoteTransactionEntity)

//    suspend fun getAllSubCategories(): List<SubCategoriesLocalModel>
    suspend fun getAllCategoryExpense(): List<CategoryLocalModel>
    suspend fun getAllCategoryIncome(): List<CategoryLocalModel>
    suspend fun getALlAccount(): List<AccountLocalModel>
    suspend fun getAllTransaction(): List<TransactionLocalModel>
    suspend fun getAllNote(): List<NoteTransactionEntity>

//    suspend fun deleteSubCategoriesById(id: Long)
    suspend fun deleteCategoryExpenseById(id: Long)
    suspend fun deleteCategoryIncomeById(id: Long)
    suspend fun deleteAccountById(id: Long)
    suspend fun deleteTransactionById(id: Long)
    suspend fun deleteNoteById(id: Long)

//    suspend fun updateSubCategories(subCategories: SubCategoriesLocalModel)
    suspend fun updateCategoryExpenses(category: CategoryLocalModel)
    suspend fun updateCategoryIncome(category: CategoryLocalModel)
    suspend fun updateAccount(account: AccountLocalModel)
    suspend fun updateTransaction(transaction: TransactionLocalModel)
    suspend fun updateNote(note: NoteTransactionEntity)

//    suspend fun transferData()
    // ném vào database
}