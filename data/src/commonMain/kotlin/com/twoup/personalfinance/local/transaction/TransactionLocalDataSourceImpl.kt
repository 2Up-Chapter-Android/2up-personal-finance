package com.twoup.personalfinance.local.transaction

import com.twoup.personalfinance.domain.model.transaction.account.AccountLocalModel
import com.twoup.personalfinance.domain.model.transaction.category.CategoryLocalModel
//import com.twoup.personalfinance.domain.model.transaction.category.subCategories.SubCategoriesLocalModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.model.transaction.note.NoteTransactionEntity
import com.twoup.personalfinance.domain.repository.transaction.TransactionLocalDataSource
import com.twoup.personalfinance.local.PersonalFinanceDatabaseWrapper
import com.twoup.personalfinance.mapping.toAccount
import com.twoup.personalfinance.mapping.toCategory
import com.twoup.personalfinance.mapping.toNote
import com.twoup.personalfinance.mapping.toTransaction
import com.twoup.personalfinance.utils.DateTimeUtil

class TransactionLocalDataSourceImpl(transactionDatabaseWrapper: PersonalFinanceDatabaseWrapper) :
    TransactionLocalDataSource {
    private val database = transactionDatabaseWrapper.instance
    private val dbQuery = database.personalFinanceDatabaseQueries

//    override suspend fun insertSubCategory(subCategories: SubCategoriesLocalModel) {
//        dbQuery.insertSubCategory(
//            subCategories.subCategories_name,
//            subCategories.category_id
//        )
//    }

    override suspend fun insertCategory(category: CategoryLocalModel) {
        dbQuery.insertCategoryItem(
            category.category_name,
        )
    }

    override suspend fun insertAccount(account: AccountLocalModel) {
        dbQuery.insertAccount(
            account.account_name,
            account.account_type,
            account.description,
            account.asset,
            account.liabilities
        )
    }

    override suspend fun insertTransaction(transaction: TransactionLocalModel) {
        dbQuery.insertTransaction(
            transaction.income,
            transaction.expenses,
            transaction.transferBalance,
            transaction.description,
            DateTimeUtil.toEpochMillis(transaction.created),
            transaction.category,
            transaction.account,
            transaction.selectIndex.toLong(),
            transaction.accountFrom,
            transaction.accountTo
        )
    }

    override suspend fun insertNote(note: NoteTransactionEntity) {
        dbQuery.insertNote(
            note.note_text,
            DateTimeUtil.toEpochMillis(note.created),
        )
    }

//    override suspend fun getAllSubCategories(): List<SubCategoriesLocalModel> {
//        dbQuery.getAllSubCategory()
//        return dbQuery.getAllSubCategory().executeAsList().map { it.toSubCategories() }
//    }

    override suspend fun getAllCategory(): List<CategoryLocalModel> {
        dbQuery.getAllCategoryItem()
        return dbQuery.getAllCategoryItem().executeAsList().map { it.toCategory() }
    }

    override suspend fun getALlAccount(): List<AccountLocalModel> {
        dbQuery.getAllAccount()
        return dbQuery.getAllAccount().executeAsList().map { it.toAccount() }
    }

    override suspend fun getAllTransaction(): List<TransactionLocalModel> {
        dbQuery.getAllTransaction()
        return dbQuery.getAllTransaction().executeAsList().map { it.toTransaction() }
    }

    override suspend fun getAllNote(): List<NoteTransactionEntity> {
        dbQuery.getAllNote()
        return dbQuery.getAllNote().executeAsList().map { it.toNote() }
    }

//    override suspend fun deleteSubCategoriesById(id: Long) {
//        dbQuery.deleteSubCategoryById(id)
//    }

    override suspend fun deleteCategoryById(id: Long) {
        dbQuery.deleteCategoryById(id)
    }

    override suspend fun deleteAccountById(id: Long) {
        dbQuery.deleteAccountById(id)
    }

    override suspend fun deleteTransactionById(id: Long) {
        dbQuery.deleteTransactionById(id)
    }

    override suspend fun deleteNoteById(id: Long) {
        dbQuery.deleteNoteById(id)
    }

//    override suspend fun updateSubCategories(subCategories: SubCategoriesLocalModel) {
//        subCategories.subCategories_id?.let {
//            dbQuery.updateSubCategoryById(
//                subCategory_name = subCategories.subCategories_name,
//                subCategory_id = it
//            )
//        }
//    }

    override suspend fun updateCategory(category: CategoryLocalModel) {
        category.category_id?.let {
            dbQuery.updagteCategoryById(
                category_id = it,
                category_name = category.category_name,
            )
        }
    }

    override suspend fun updateAccount(account: AccountLocalModel) {
        account.account_id?.let {
            dbQuery.updateAccountById(
                account_id = it,
                account_name = account.account_name,
                income = account.asset,
                expense = account.liabilities
            )
        }
    }

    override suspend fun updateTransaction(transaction: TransactionLocalModel) {
        transaction.transaction_id?.let {
            dbQuery.updateTransactionById(
                income = transaction.income!!.toLong(),
                expenses = transaction.expenses!!.toLong(),
                description = transaction.description,
                transaction_id = it
            )
        }
    }

    override suspend fun updateNote(note: NoteTransactionEntity) {
        note.note_id?.let {
            dbQuery.updateNoteById(
                note_id = it,
                note_text = note.note_text
            )
        }
    }
}