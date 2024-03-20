package com.aicontent.status.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.aay.compose.donutChart.PieChart
import com.aay.compose.donutChart.model.PieChartData

class StatusScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { StatusViewModel() }

        Scaffold {
            PieChartSample(viewModel)
        }


    }

    @Composable
    fun PieChartSample(viewModel: StatusViewModel) {

        val transaction = viewModel.transaction.value
        val totalIncome = viewModel.calculateTotalIncome(transaction)
        val totalExpenses = viewModel.calculateTotalExpenses(transaction)

        val testPieChartData: List<PieChartData> = listOf(
            PieChartData(
                partName = "Expenses",
                data = totalExpenses.toDouble(),
                color = Color(0xFF22A699),
            ),
            PieChartData(
                partName = "Income",
                data = totalIncome.toDouble(),
                color = Color(0xFFF2BE22),
            )
        )

        PieChart(
            modifier = Modifier.fillMaxSize(),
            pieChartData = testPieChartData,
            ratioLineColor = Color.LightGray,
            textRatioStyle = TextStyle(color = Color.Gray),
        )
    }
}