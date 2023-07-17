package com.twoup.personalfinance.transaction.presentation.createTransaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AccountsModalBottomSheetLayout() {
    Box(modifier = Modifier.fillMaxWidth().height(500.dp)) {
        Column {
            Row(
                modifier = Modifier.padding().background(color = Color.LightGray)
                    .padding(start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Accounts", modifier = Modifier.weight(1f))
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = null
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextButton(
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    Text("Bank account", color = Color.Black)
                }

                Spacer(
                    modifier = Modifier.width(1.dp).height(48.dp).background(Color.Gray)
                )

                TextButton(
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    Text("Card", color = Color.Black)
                }
                Spacer(
                    modifier = Modifier.width(1.dp).height(48.dp).background(Color.Gray)
                )
                TextButton(
                    onClick = {},
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    Text("Cash", color = Color.Black)
                }
            }
            Divider(thickness = 1.dp, color = Color.Gray)
        }
    }
}

@Composable
fun CategoryModalBottomSheetLayout() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Row(
                modifier = Modifier
                    .padding()
                    .background(color = Color.LightGray)
                    .padding(start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Category", modifier = Modifier.weight(1f))
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = null
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
            }

            Row() {
                Column(modifier = Modifier.weight(1f)) {
                    val buttonModifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth()
                        .background(Color.White)

                    TextButton(
                        onClick = {},
                        modifier = buttonModifier
                    ) {
                        Text("self grow", color = Color.Black)
                    }

                    Divider(
                        thickness = 1.dp,
                        color = Color.Gray
                    )

                    TextButton(
                        onClick = {},
                        modifier = buttonModifier
                    ) {
                        Text("entertainment", color = Color.Black)
                    }

                    Divider(
                        thickness = 1.dp,
                        color = Color.Gray
                    )

                    TextButton(
                        onClick = {},
                        modifier = buttonModifier
                    ) {
                        Text("Education", color = Color.Black)
                    }
                    Divider(
                        thickness = 1.dp,
                        color = Color.Gray
                    )

                    TextButton(
                        onClick = {},
                        modifier = buttonModifier
                    ) {
                        Text("Food", color = Color.Black)
                    }
                    Divider(
                        thickness = 1.dp,
                        color = Color.Gray
                    )

                    TextButton(
                        onClick = {},
                        modifier = buttonModifier
                    ) {
                        Text("event", color = Color.Black)
                    }
                    Divider(
                        thickness = 1.dp,
                        color = Color.Gray
                    )

                    TextButton(
                        onClick = {},
                        modifier = buttonModifier
                    ) {
                        Text("shirt", color = Color.Black)
                    }
                    Divider(
                        thickness = 1.dp,
                        color = Color.Gray
                    )

                    TextButton(
                        onClick = {},
                        modifier = buttonModifier
                    ) {
                        Text("hobbies", color = Color.Black)
                    }
                    Divider(
                        thickness = 1.dp,
                        color = Color.Gray
                    )

                    TextButton(
                        onClick = {},
                        modifier = buttonModifier
                    ) {
                        Text("etc", color = Color.Black)
                    }
                    Divider(
                        thickness = 1.dp,
                        color = Color.Gray
                    )

                }
                Spacer(
                    modifier = Modifier.width(1.dp).fillMaxHeight().background(Color.Gray)
                )

                Column(Modifier.weight(1f)) {
                    val buttonModifier = Modifier
//                        .weight(1f)
                        .height(48.dp)
                        .fillMaxWidth()
                        .background(Color.White)

                    TextButton(
                        onClick = {},
                        modifier = buttonModifier
                    ) {
                        Text("afg", color = Color.Black)
                    }
                    Divider(
//                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = Color.Gray
                    )
                    TextButton(
                        onClick = {},
                        modifier = buttonModifier
                    ) {
                        Text("gfadsf", color = Color.Black)
                    }
                    Divider(
//                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}