package com.example.lechendasapp.utils


import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import com.example.lechendasapp.views.SearchItem
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt


// Estructura de datos para representar los datos del Donut Chart
data class DonutChartData(
    val amount: Float,
    val color: Color,
    val title: String
)

class DonutChartDataCollection(val items: List<DonutChartData>) {
    val totalAmount: Float = items.fold(0f) { acc, item -> acc + item.amount }

}

// Función para convertir números a formato de moneda
fun Float.toMoneyFormat(withDecimals: Boolean = false): String {
    return if (withDecimals) String.format("%.2f", this) else String.format("%.0f", this)
}

@Composable
fun DonutChartScreen() {
    val viewData = DonutChartDataCollection(
        listOf(
            DonutChartData(1200.0f, MaterialTheme.colorScheme.primary, title = "Food & Groceries"),
            DonutChartData(1500.0f, MaterialTheme.colorScheme.secondary, title = "Rent"),
            DonutChartData(300.0f, MaterialTheme.colorScheme.tertiary, title = "Gas"),
            DonutChartData(700.0f, MaterialTheme.colorScheme.onPrimary, title = "Online Purchases"),
            DonutChartData(300.0f, MaterialTheme.colorScheme.onSecondary, title = "Clothing")
        )
    )

    Scaffold(
        topBar = {

        }
    ) { paddingValues ->
        DonutChart(Modifier.padding(paddingValues), data = viewData) { selected ->
            AnimatedContent(targetState = selected) {
                val amount = it?.amount ?: viewData.totalAmount
                val text = it?.title ?: "Total"
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$${amount.toMoneyFormat(true)}",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Gray
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.LightGray
                    )
                }
            }
        }
    }
}


@Composable
fun DonutChart(
    modifier: Modifier = Modifier,
    data: DonutChartDataCollection,
    chartSize: Dp = 350.dp,
    gapPercentage: Float = 0.04f,
    selectionView: @Composable (selectedItem: DonutChartData?) -> Unit = {},
) {
    val anglesList: MutableList<DrawingAngles> = remember { mutableListOf() }
    val gapAngle = data.calculateGapAngle(gapPercentage)
    var selectedIndex by remember { mutableStateOf(-1) }

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            modifier = Modifier.size(chartSize),
            onDraw = {
                val defaultStrokeWidth = 40.dp.toPx()
                anglesList.clear()
                var lastAngle = 0f
                data.items.forEachIndexed { ind, item ->
                    val sweepAngle = data.findSweepAngle(ind, gapPercentage)
                    anglesList.add(DrawingAngles(lastAngle, sweepAngle))
                    drawArc(
                        color = item.color,
                        startAngle = lastAngle,
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        topLeft = Offset(defaultStrokeWidth / 2, defaultStrokeWidth / 2),
                        style = Stroke(defaultStrokeWidth, cap = StrokeCap.Butt),
                        size = Size(size.width - defaultStrokeWidth, size.height - defaultStrokeWidth)
                    )
                    lastAngle += sweepAngle + gapAngle
                }
            }
        )
    }
}

// Representa los ángulos en los que cada sección de la dona es dibujada
data class DrawingAngles(val startAngle: Float, val sweepAngle: Float)

// Extensiones para cálculos de la brecha entre arcos y ángulos
private const val TOTAL_ANGLE = 360f

private fun DonutChartDataCollection.calculateGapAngle(gapPercentage: Float): Float {
    val gap = this.calculateGap(gapPercentage)
    return (gap / this.getTotalAmountWithGapIncluded(gapPercentage)) * TOTAL_ANGLE
}

private fun DonutChartDataCollection.findSweepAngle(
    index: Int,
    gapPercentage: Float
): Float {
    val amount = items[index].amount
    val gap = this.calculateGap(gapPercentage)
    val totalWithGap = getTotalAmountWithGapIncluded(gapPercentage)
    val gapAngle = this.calculateGapAngle(gapPercentage)
    return ((((amount + gap) / totalWithGap) * TOTAL_ANGLE)) - gapAngle
}

private fun DonutChartDataCollection.calculateGap(gapPercentage: Float): Float {
    if (this.items.isEmpty()) return 0f
    return (this.totalAmount / this.items.size) * gapPercentage
}

private fun DonutChartDataCollection.getTotalAmountWithGapIncluded(gapPercentage: Float): Float {
    val gap = this.calculateGap(gapPercentage)
    return this.totalAmount + (this.items.size * gap)
}

