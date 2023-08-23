package com.twoup.personalfinance.mapping

import com.twoup.personalfinance.domain.model.transaction.account.AccountLocalModel
import com.twoup.personalfinance.domain.model.transaction.category.CategoryLocalModel
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.model.transaction.note.NoteTransactionEntity
import comtwouppersonalfinancedatabase.Accounts
import comtwouppersonalfinancedatabase.Categories
import comtwouppersonalfinancedatabase.Notes
import comtwouppersonalfinancedatabase.Transactions
import kotlinx.datetime.Instant
import kotlinx.datetime.toLocalDateTime

fun Accounts.toAccount(): AccountLocalModel{
    return AccountLocalModel(
        account_id = account_id,
        account_name = account_name,
        account_type = account_type,
        description = description,
        income = income,
        expense = expense
    )
}

//fun SubCategories.toSubCategories(): SubCategoriesLocalModel{
//    return SubCategoriesLocalModel(
//        subCategories_id  = subCategory_id,
//        subCategories_name = subCategory_name,
//        category_id = category_id
//    )
//}

fun Categories.toCategory(): CategoryLocalModel{
    return CategoryLocalModel(
        category_id = category_id,
        category_name = category_name,
    )
}

fun Transactions.toTransaction(): TransactionLocalModel{
    return TransactionLocalModel(
        transaction_id = transaction_id,
        amount = amount,
        description = description,
        created = Instant.fromEpochMilliseconds(created).toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
        category = category,
        account = account
    )
}

fun Notes.toNote(): NoteTransactionEntity{
    return NoteTransactionEntity(
        note_id = note_id,
        note_text = note_text,
        created = Instant.fromEpochMilliseconds(created).toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
    )
}
