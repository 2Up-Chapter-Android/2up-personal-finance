package com.twoup.personalfinance.domain.usecase.localTransaction.transaction

import androidx.compose.runtime.mutableStateListOf
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.domain.repository.transaction.TransactionLocalDataSource
import com.twoup.personalfinance.utils.DateTimeUtil
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UseCaseSearchTransactionByNote(private val dataSource: TransactionLocalDataSource) {

    val listTransactionState: MutableStateFlow<List<TransactionLocalModel>> =
        MutableStateFlow(listOf())

    private val _searchResults = mutableStateListOf<TransactionLocalModel>()
    private val _searchResultsFlow = MutableStateFlow(_searchResults.toList())
    //    val filterTagNotes : StateFlow<List<NoteEntity>> get() = _filterNoteTags
    val searchResults: StateFlow<List<TransactionLocalModel>> get() = _searchResultsFlow

    @OptIn(DelicateCoroutinesApi::class)
    fun searchTransaction(note: String, description: String) {
        GlobalScope.launch {
            val transaction = withContext(Dispatchers.Default) {
                dataSource.searchTransaction(note, description)
            }
            listTransactionState.value = transaction
        }
    }
    @OptIn(DelicateCoroutinesApi::class)
    fun searchTransaction(query: String) {
        if (query.isBlank()) {
            _searchResults.clear()
            return
        }
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val allTransaction = dataSource.getAllTransaction()
                val filteredNotes = SearchNoteFromOldest().execute(allTransaction, query)
                _searchResults.clear()
                _searchResults.addAll(filteredNotes)
                _searchResultsFlow.value = _searchResults.toList()
            } catch (e: Exception) {
                // Handle the exception accordingly (e.g., log, display error message)
                e.printStackTrace()
            }
        }
    }
    class SearchNoteFromOldest {
        fun execute(transactions: List<TransactionLocalModel>, query: String): List<TransactionLocalModel> {
            if(query.isBlank()) {
                return transactions
            }
            return transactions.filter {
                it.transactionNote.trim().lowercase().contains(query.lowercase()) ||
                        it.transactionDescription.trim().lowercase().contains(query.lowercase())
            }.sortedBy {
                DateTimeUtil.toEpochMillis(it.transactionCreated)
            }
        }
    }
}
