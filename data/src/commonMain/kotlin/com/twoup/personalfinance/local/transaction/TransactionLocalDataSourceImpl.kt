package com.twoup.personalfinance.local.transaction

import com.twoup.personalfinance.domain.model.transaction.account.AccountLocalModel
import com.twoup.personalfinance.domain.model.transaction.category.CategoryLocalModel
//import com.twoup.personalfinance.domain.model.transaction.category.subCategories.SubCategoriesLocalModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.model.transaction.note.NoteTransactionEntity
import com.twoup.personalfinance.domain.repository.transaction.TransactionLocalDataSource
import com.twoup.personalfinance.local.PersonalFinanceDatabaseWrapper
import com.twoup.personalfinance.mapping.toAccount
import com.twoup.personalfinance.mapping.toCategoryExpenses
import com.twoup.personalfinance.mapping.toCategoryIncome
import com.twoup.personalfinance.mapping.toNote
import com.twoup.personalfinance.mapping.toTransaction
import com.twoup.personalfinance.utils.DateTimeUtil

class TransactionLocalDataSourceImpl(transactionDatabaseWrapper: PersonalFinanceDatabaseWrapper) :
    TransactionLocalDataSource {
    private val database = transactionDatabaseWrapper.instance
    private val dbQuery = database.personalFinanceDatabaseQueries
    override suspend fun insertCategoryExpenses(category: CategoryLocalModel) {
        dbQuery.insertCategoryExpenses(
            category.category_name,
        )
    }
    override suspend fun insertCategoryIncome(category: CategoryLocalModel) {
        dbQuery.insertCategoryIncome(
            category.category_name,
        )
    }
    override suspend fun insertAccount(account: AccountLocalModel) {
        dbQuery.insertAccount(
            account.account_name,
            account.account_type,
            account.account_description,
            account.account_asset,
            account.account_liabilities
        )
    }
    override suspend fun insertTransaction(transaction: TransactionLocalModel) {
        dbQuery.insertTransaction(
            transaction.transaction_income,
            transaction.transaction_expenses,
            transaction.transaction_transfer,
            transaction.transaction_description,
            transaction.transaction_note,
            DateTimeUtil.toEpochMillis(transaction.transaction_created),
            transaction.transaction_category,
            transaction.transaction_account,
            transaction.transaction_selectIndex.toLong(),
            transaction.transaction_accountFrom,
            transaction.transaction_accountTo
        )
    }
    override suspend fun insertNote(note: NoteTransactionEntity) {
        dbQuery.insertNote(
            note.note_text,
            note.note_title,
            DateTimeUtil.toEpochMillis(note.created),
        )
    }
    override suspend fun getAllCategoryExpense(): List<CategoryLocalModel> {
        dbQuery.getAllCategoryExpenses()
        return dbQuery.getAllCategoryExpenses().executeAsList().map { it.toCategoryExpenses() }
    }
    override suspend fun getAllCategoryIncome(): List<CategoryLocalModel> {
        dbQuery.getAllCategoryIncome()
        return dbQuery.getAllCategoryIncome().executeAsList().map { it.toCategoryIncome() }
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
    override suspend fun deleteCategoryExpenseById(id: Long) {
        dbQuery.deleteCategoryExpensesById(id)
    }
    override suspend fun deleteCategoryIncomeById(id: Long) {
        dbQuery.deleteCategoryIncomeById(id)
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
    override suspend fun updateCategoryExpenses(category: CategoryLocalModel) {
        category.category_id?.let {
            dbQuery.updagteCategoryExpensesById(
                category_id = it,
                category_name = category.category_name,
            )
        }
    }
    override suspend fun updateCategoryIncome(category: CategoryLocalModel) {
        category.category_id?.let {
            dbQuery.updagteCategoryIncomeById(
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
                account_asset = account.account_asset,
                account_liabilities = account.account_liabilities
            )
        }
    }
    override suspend fun updateTransaction(transaction: TransactionLocalModel) {
        transaction.transaction_id?.let {
            dbQuery.updateTransactionById(
                transaction_income = transaction.transaction_income,
                transaction_expenses = transaction.transaction_expenses,
                transaction_description = transaction.transaction_description,
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