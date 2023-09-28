package com.twoup.personalfinance.domain.model.transaction.note

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class NoteTransactionEntity(
    val note_id: Long?,
    val note_title : String,
    val note_text: String,
    val created: LocalDateTime,
)