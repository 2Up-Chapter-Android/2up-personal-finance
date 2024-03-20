import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ContentAlpha
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aicontent.main.presentation.MainScreenViewModel
import io.github.aakira.napier.Napier
import kotlinx.datetime.LocalDateTime
import kotlin.math.roundToInt

@Composable
fun CalendarView(
    viewModel: MainScreenViewModel
) {
    var monthIndicator by remember { mutableStateOf(0) }
    val animatedIndicator by animateIntAsState(
        targetValue = monthIndicator,
        animationSpec = tween(durationMillis = 500) // Customize the animation duration and easing
    )

    val totalDragAmount = remember { mutableStateOf(0f) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                totalDragAmount.value = 0f
                detectHorizontalDragGestures { _, dragAmount ->
                    val threshold = 500f // Lowered threshold for easier swiping

                    // Accumulate dragAmount.x over time.
                    totalDragAmount.value += dragAmount

                    // Check if the accumulated drag is beyond the threshold
                    if (totalDragAmount.value > threshold) {
                        totalDragAmount.value = 0f // Reset immediately to prevent multiple detections
                        monthIndicator-- // Decrement to indicate a month change
                        viewModel.decrementMonth()

                    } else if (totalDragAmount.value < -threshold) {
                        totalDragAmount.value = 0f // Reset immediately to prevent multiple detections
                        monthIndicator++ // Increment to indicate a month change
                        viewModel.incrementMonth()
                    }
                }
            }
    ) {
        CalendarGrid(
            viewModel
        )
    }
}

@Composable
fun CalendarGrid(
    viewModel: MainScreenViewModel
) {
//    val dayList by viewModel.calendarDays.collectAsState()
    val dayList by viewModel.generateCalendarData(viewModel.currentMonthYear).collectAsState()
    val listTrans = viewModel.transactionByMonth.collectAsState().value
    val distinctTransactions = listTrans.distinctBy { it.transactionCreated.date.dayOfMonth}
//    val allTransaction = viewModel.transaction.value

    Napier.d(message = " daylist = $dayList", tag = "test")
    LazyColumn(
        modifier = Modifier.fillMaxSize().border(0.5.dp, Color.Gray)
    ) {
        item {
            // Weekdays
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (day in WeekDay.entries) {
                    Text(
                        text = day.name.take(3),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f).border(0.5.dp, Color.Gray),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        itemsIndexed(dayList.chunked(7)) { index,weekList ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 0.dp, max = (LocalDensity.current.density * 90 * 6).dp)
            ) {
                for (day in weekList) {
                    val isCurrentMonth = isCurrentMonth(day,viewModel.currentMonthYear.value)
                    val backgroundColor = when{
                        isCurrentMonth -> Color.White
                        else -> Color.Gray.copy(0.1f)
                    }
                    Napier.d(message = "$backgroundColor", tag = "color")
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(90.dp)
                            .border(0.5.dp, Color.Gray)
                            .background(color = backgroundColor)
                    ) {
                        Column(modifier = Modifier.fillMaxSize().padding(2.dp)) {
                            Row {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = day.dayOfMonth.toString(),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 10.sp

                                    ),
                                    color = when {
                                        isCurrentMonth -> MaterialTheme.colorScheme.onSurface
                                        else -> MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.medium)
                                    }
                                )
                                Spacer(modifier = Modifier.padding(2.dp))
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = day.month.toString().take(3),
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 10.sp

                                    ),
                                    color = when {
                                        isCurrentMonth -> MaterialTheme.colorScheme.onSurface
                                        else -> MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.medium)
                                    }
                                )
                            }

                            val transDate = distinctTransactions.filter { it.transactionCreated.date == day.date }
                            Napier.d { "transDate = $distinctTransactions" }
                            Text(
                                text = if (transDate.isNotEmpty()) transDate[0].transactionIncome.toString() else "",
                                )
//                            Text(
//                                modifier = Modifier.align(Alignment.End).weight(1f),
//                                text = backgroundColor.value.toString(),
//                                style = MaterialTheme.typography.bodySmall.copy(
//                                    fontWeight = FontWeight.Normal,
//                                    fontSize = 8.sp
//                                ),
//                                color = Color.Red,
//                            )
//                            Text(
//                                text = if (viewModel.calculateTotalIncome(allTransaction.filter { it.transactionCreated.date == day.date })
//                                        .toInt() != 0
//                                )
//                                    viewModel.calculateTotalIncome(allTransaction.filter { it.transactionCreated.date == day.date })
//                                        .toString()
//                                else
//                                    "",
//                                style = MaterialTheme.typography.bodySmall.copy(
//                                    fontWeight = FontWeight.Normal,
//                                    fontSize = 8.sp
//                                ),
//                                color = Color.Blue,
//                                modifier = Modifier.align(Alignment.End).weight(1f),
//                            )

//                            Text(
//                                modifier = Modifier.align(Alignment.End).weight(1f),
//                                text = if (viewModel.calculateTotalExpenses(allTransaction.filter { it.transactionCreated.date == day.date })
//                                        .toInt() != 0
//                                )
//                                    viewModel.calculateTotalExpenses(allTransaction.filter { it.transactionCreated.date == day.date })
//                                        .toString()
//                                else
//                                    "",
//                                style = MaterialTheme.typography.bodySmall.copy(
//                                    fontWeight = FontWeight.Normal,
//                                    fontSize = 8.sp
//                                ),
//                                color = Color.Red,
//                            )

                        }
                    }
                }
            }
        }
    }
}

fun isCurrentMonth(
    day: LocalDateTime,
    currentMonthYear: MainScreenViewModel.MonthYear
): Boolean {
    return day.monthNumber == currentMonthYear.month
}

enum class WeekDay {
    Monday,
    Tuesday,
    Wednesday,
    Thursday,
    Friday,
    Saturday,
    Sunday,
}
