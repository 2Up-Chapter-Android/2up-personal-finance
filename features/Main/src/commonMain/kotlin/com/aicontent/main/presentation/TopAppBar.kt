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
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.aicontent.main.presentation.daily.DailyScreen
import com.twoup.personalfinance.navigation.MainScreenSharedScreen
import cafe.adriel.voyager.core.model.rememberScreenModel

@Composable
fun TopAppBar(
    onSearchClicked: () -> Unit,
    onBookMark: () -> Unit,
    onAnalysis: () -> Unit,
    selectedTabIndex: Int
) {
    val navigator = LocalNavigator.currentOrThrow
    val dailyScreen = rememberScreen(MainScreenSharedScreen.DailyScreen)
    val calendarScreen = rememberScreen(MainScreenSharedScreen.CalendarScreen)
    val monthlyScreen = rememberScreen(MainScreenSharedScreen.MonthlyScreen)
    val totalScreen = rememberScreen(MainScreenSharedScreen.TotalScreen)
    val noteScreen = rememberScreen(MainScreenSharedScreen.NoteScreen)

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // First row
            Row(
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .background(color = MaterialTheme.colors.surface, RoundedCornerShape(16.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {},
                    content = {
                        Icon(
                            Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                )

                Text(
                    text = "Jun 2023",
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                IconButton(
                    onClick = {},
                    content = {
                        Icon(
                            Icons.Default.KeyboardArrowRight,
                            contentDescription = "Next"
                        )
                    }
                )
            }

            // Second row
            Row(
                modifier = Modifier
                    .height(56.dp)
                    .background(color = MaterialTheme.colors.surface, RoundedCornerShape(16.dp)),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBookMark,
                    content = {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = "Star"
                        )
                    }
                )

                IconButton(
                    onClick = onSearchClicked,
                    content = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }
                )

                IconButton(
                    onClick = onAnalysis,
                    content = {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "More",
                        )
                    }
                )
            }
        }
        // Tabs
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
        ) {
            Tab(
                selected = selectedTabIndex == 0,
                onClick = {
//                    selectedTabIndex = 0
                    navigator.push(dailyScreen)
                }, // Set the correct index for the first tab
                content = {
                    Text(
                        text = "Daily",
                        color = if (selectedTabIndex == 0) Color.Black else Color.Gray,
                        modifier = Modifier.padding(16.dp),
                        fontSize = 12.sp
                    )
                }
            )
            Tab(
                selected = selectedTabIndex == 1,
                onClick = {
//                    selectedTabIndex = 1
                    navigator.push(calendarScreen)
                }, // Set the correct index for the second tab
                content = {
                    Text(
                        text = "Calendar",
                        color = if (selectedTabIndex == 1) Color.Black else Color.Gray,
                        modifier = Modifier.padding(16.dp),
                        fontSize = 12.sp
                    )
                }
            )
            Tab(
                selected = selectedTabIndex == 2,
                onClick = {
//                    selectedTabIndex = 2
                    navigator.push(monthlyScreen)
                }, // Set the correct index for the third tab
                content = {
                    Text(
                        text = "Monthly",
                        color = if (selectedTabIndex == 2) Color.Black else Color.Gray,
                        modifier = Modifier.padding(16.dp),
                        fontSize = 12.sp
                    )
                }
            )
            Tab(
                selected = selectedTabIndex == 3,
                onClick = {
//                    selectedTabIndex = 3
                    navigator.push(totalScreen)
                }, // Set the correct index for the fourth tab
                content = {
                    Text(
                        text = "Total",
                        color = if (selectedTabIndex == 3) Color.Black else Color.Gray,
                        modifier = Modifier.padding(16.dp),
                        fontSize = 12.sp
                    )
                }
            )
            Tab(
                selected = selectedTabIndex == 4,
                onClick = {
//                    selectedTabIndex = 4
                    navigator.push(noteScreen)
                }, // Set the correct index for the fifth tab
                content = {
                    Text(
                        text = "Note",
                        color = if (selectedTabIndex == 4) Color.Black else Color.Gray,
                        modifier = Modifier.padding(16.dp),
                        fontSize = 12.sp
                    )
                }
            )
        }
    }
}






