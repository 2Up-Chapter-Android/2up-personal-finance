package com.aicontent.main.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.navigation.TransactionSharedScreen
import com.twoup.personalfinance.utils.DateTimeUtil

class SearchScreen() : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { MainScreenViewModel() }
        val searchQuery = remember { mutableStateOf("") }
        val searchResults by viewModel.searchResults.collectAsState()
        val navigator = LocalNavigator.currentOrThrow
//        val selectedNote by viewModel.selectedTransaction.collectAsState(null)

        Column(modifier = Modifier.fillMaxSize().background(colors.surface)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .background(color = colors.surface, RoundedCornerShape(16.dp))
                ) {
                    TextField(
                        value = searchQuery.value,
                        onValueChange = { query ->
                            searchQuery.value = query
                            viewModel.searchTransaction(query)
                        },
                        textStyle = TextStyle(color = colors.onPrimary),
                        placeholder = {
                            Text(text = "Search")
                        },
                        modifier = Modifier.fillMaxSize(),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                            }
                        ),
                        leadingIcon = {
                            IconButton(
                                onClick = {
                                    navigator.pop()
                                }
                            ) {
                                Icon(
                                    Icons.Default.ArrowBack,
                                    contentDescription = "Back",
                                    tint = androidx.compose.ui.graphics.Color.Black
                                )
                            }
                        },
                        trailingIcon = {
                            if (searchQuery.value.isNotEmpty()) {
                                IconButton(
                                    onClick = {
                                        searchQuery.value = ""
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.Clear,
                                        contentDescription = "Clear Icon",
                                        tint = colors.primaryVariant
                                    )
                                }
                            }
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = colors.surface,
                            cursorColor = colors.onPrimary,
                            focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                            unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
                        ),
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (searchResults.value.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                    items(searchResults.value) { transaction ->
                        val editScreen = rememberScreen(
                            TransactionSharedScreen.CreateTransactionScreen(
                                transaction.transactionId
                            )
                        )
                        ItemNotesSearch(
                            transaction,
                        ) { navigator.push(editScreen) }
                    }
                }
            } else {
                Text(
                    text = "No results found",
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )
            }
        }
    }

    @Composable
    fun ItemNotesSearch(
        transaction: TransactionLocalModel,
        onNoteClick: () -> Unit,
    ) {
        val formattedDate = remember(transaction.transactionCreated) {
            DateTimeUtil.formatNoteDate(transaction.transactionCreated)
        }

        Card(
            elevation = 4.dp,
            shape = MaterialTheme.shapes.medium,
            backgroundColor = androidx.compose.ui.graphics.Color.White,
            border = BorderStroke(1.dp, androidx.compose.ui.graphics.Color.LightGray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clip(MaterialTheme.shapes.medium)
                .clickable(onClick = { onNoteClick() })
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                TransactionTitle(transaction.transactionNote)
                TransactionDescription(transaction.transactionDescription)
                TransactionDate(formattedDate, modifier = Modifier.align(Alignment.End))
            }
        }
    }

    @Composable
    fun TransactionTitle(title: String) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            fontStyle = FontStyle.Italic
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

    @Composable
    fun TransactionDescription(description: String) {
        Text(
            text = description,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
    }

    @Composable
    fun TransactionDate(date: String, modifier: Modifier) {
        Text(
            text = date,
            color = androidx.compose.ui.graphics.Color.DarkGray,
            modifier = modifier
        )
    }
}
