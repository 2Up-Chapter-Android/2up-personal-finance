package com.twoup.personalfinance.mapping

import com.twoup.personalfinance.domain.model.transaction.account.AccountLocalModel
import com.twoup.personalfinance.domain.model.transaction.category.CategoryLocalModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.model.transaction.note.NoteTransactionEntity
import com.twoup.personalfinance.resources.MR
import comtwouppersonalfinancedatabase.Accounts
import comtwouppersonalfinancedatabase.CategorieExpense
import comtwouppersonalfinancedatabase.CategorieIncome
import comtwouppersonalfinancedatabase.Notes
import comtwouppersonalfinancedatabase.Transactions
import kotlinx.datetime.Instant
import kotlinx.datetime.toLocalDateTime

fun Accounts.toAccount(): AccountLocalModel{
    return AccountLocalModel(
        account_id = account_id,
        account_name = account_name,
        account_type = account_type,
        account_description = account_description,
        account_asset  = account_asset,
        account_liabilities = account_liabilities
    )
}
fun CategorieExpense.toCategoryExpenses(): CategoryLocalModel{
    return CategoryLocalModel(
        category_id = category_id,
        category_name = category_name,
    )
}
fun CategorieIncome.toCategoryIncome(): CategoryLocalModel{
    return CategoryLocalModel(
        category_id = category_id,
        category_name = category_name,
    )
}
fun Transactions.toTransaction(): TransactionLocalModel{
    return TransactionLocalModel(
        transaction_id = transaction_id,
        transaction_income = transaction_income,
        transaction_expenses = transaction_expenses,
        transaction_transfer = transaction_transfer,
        transaction_description = transaction_description,
        transaction_note = transaction_note,
        transaction_created = Instant.fromEpochMilliseconds(transaction_created).toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
        transaction_month = transaction_month,
        transaction_year = transaction_year,
        transaction_category = transaction_category,
        transaction_account = transaction_account,
        transaction_selectIndex = transaction_selectIndex.toInt(),
        transaction_accountFrom = transaction_accountFrom,
        transaction_accountTo = transaction_accountTo
    )
}

fun Notes.toNote(): NoteTransactionEntity{
    return NoteTransactionEntity(
        note_id = note_id,
        note_title = note_title,
        note_text = note_text,
        created = Instant.fromEpochMilliseconds(note_created).toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
    )
}
