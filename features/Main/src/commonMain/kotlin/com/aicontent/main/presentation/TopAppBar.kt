package com.aicontent.main.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.aicontent.main.theme.font_size_text_tab
import com.aicontent.main.theme.height_row_top_bar
import com.aicontent.main.theme.padding_tab_item
import com.aicontent.main.theme.padding_text_top_bar
import com.aicontent.main.theme.rounded_corner_shape
import PersonalFinance.features.Main.MR
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.twoup.personalfinance.domain.model.transaction.createTrans.TransactionLocalModel
import com.twoup.personalfinance.utils.DateTimeUtil
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.desc

@Composable
fun TopAppBar(
    onSearchClicked: () -> Unit,
    onBookMark: () -> Unit,
    onAnalysis: () -> Unit,
    viewModel: MainScreenViewModel,
) {
    Column {
        FirstRow(onBookMark, onSearchClicked, onAnalysis, viewModel)
        TabRowWithTabs(viewModel.selectedTabIndex.value) { newIndex ->
            viewModel.selectedTabIndex.value = newIndex
        }
    }
}

@Composable
private fun FirstRow(
    onBookMark: () -> Unit,
    onSearchClicked: () -> Unit,
    onAnalysis: () -> Unit,
    viewModel: MainScreenViewModel,
) {
    val currentMonthYear = viewModel.currentMonthYear

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .height(height_row_top_bar)
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(rounded_corner_shape)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    viewModel.decrementMonth() // Decrease the month and year
                },
                content = {
                    Icon(
                        Icons.Default.KeyboardArrowLeft,
                        contentDescription = null
                    )
                }
            )

            BoldMonthText(viewModel.getAbbreviatedMonth(currentMonthYear.value.month), currentMonthYear.value.year)

            IconButton(
                onClick = {
                    viewModel.incrementMonth() // Increase the month and year
                },
                content = {
                    Icon(
                        Icons.Default.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            )
        }

        // Second row
        Row(
            modifier = Modifier
                .height(height_row_top_bar)
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(rounded_corner_shape)
                ),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBookMark,
                content = {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null
                    )
                }
            )

            IconButton(
                onClick = onSearchClicked,
                content = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null
                    )
                }
            )

            IconButton(
                onClick = onAnalysis,
                content = {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = null
                    )
                }
            )
        }
    }
}


@Composable
private fun TabRowWithTabs(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)

    ) {
        val tabs = listOf(
            TabInfo(MR.strings.daily.desc().localized(), 0),
            TabInfo(MR.strings.calendar.desc().localized(), 1),
            TabInfo(MR.strings.monthly.desc().localized(), 2),
            TabInfo(MR.strings.total.desc().localized(), 3),
            TabInfo(MR.strings.note.desc().localized(), 4)
        )

        tabs.forEach { tabIndex ->
            Tab(
                selected = selectedTabIndex == tabIndex.index,
                onClick = { onTabSelected(tabIndex.index) },
                content = {
                    Text(
                        text = tabIndex.title,
                        color = if (selectedTabIndex == tabIndex.index) Color.Black else Color.Gray,
                        modifier = Modifier.padding(padding_tab_item),
                        fontSize = font_size_text_tab
                    )
                },
            )
        }
    }
}

@Composable
fun BoldMonthText(month: String, year: Int) {
    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(month)
        }
        append(".")
        append(year.toString())
    }

    Text(
        text = text,
        color = Color.Black, // Change the color as needed
        modifier = Modifier.padding(horizontal = padding_text_top_bar),
        fontSize = 16.sp // Change the font size as needed
    )
}

data class TabInfo(val title: String, val index: Int)