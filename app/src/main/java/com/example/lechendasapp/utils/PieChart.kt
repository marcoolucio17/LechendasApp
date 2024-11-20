package com.example.lechendasapp.utils

import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import com.example.lechendasapp.viewmodels.PieChartViewModel
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.min

@Composable
fun AnimatedCircleChart(
    totalAmount: Float,
    parts: List<Float>,
    partNames: List<String>,
    colors: List<Color>,
    modifier: Modifier = Modifier.size(300.dp)
) {
    // Validate input
    require(parts.size == partNames.size && parts.size == colors.size) {
        "Parts, partNames, and colors lists must have equal length"
        Log.d("Parts size: ", parts.size.toString())
        Log.d("partNames size: ", partNames.size.toString())
        Log.d("colors size: ", colors.size.toString())
    }

    // Calculate proportions and angles
    val totalParts = parts.sum()
    val angles = parts.map { 360 * (it / totalParts) }
    val percentages = parts.map { it / totalParts }

    // State to track selected part
    var selectedPartIndex by remember { mutableIntStateOf(-1) }

    // Central text to display
    val centerText = if (selectedPartIndex >= 0) {
        "${partNames[selectedPartIndex]}\n" +
                "${parts[selectedPartIndex]} (${(percentages[selectedPartIndex] * 100).toInt()}%)"
    } else {
        "Total: $totalAmount"
    }

    // Animation for slice scaling
    val animatedScales = parts.indices.map { index ->
        animateFloatAsState(
            targetValue = if (selectedPartIndex == index) 1.1f else 1.0f,
            animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
        ).value
    }

    // Chart
    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .matchParentSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { tapOffset ->
                            val center = Offset(x = size.width / 2f, y = size.height / 2f)
                            val radius = min(size.width, size.height) / 2f

                            // Check if tap is inside the chart
                            val isInsideChart = (tapOffset - center).getDistance() <= radius

                            if (isInsideChart) {
                                // Calculate touch angle and find hit index
                                val touchAngle = calculateTouchAngle(center, tapOffset)
                                val hitIndex = findHitIndex(touchAngle, angles)

                                // Toggle selection
                                selectedPartIndex = if (selectedPartIndex == hitIndex) -1 else hitIndex
                            } else {
                                // Reset selection if tapped outside
                                selectedPartIndex = -1
                            }
                        }
                    )
                }
        ) {
            var startAngle = 0f
            val radius = min(size.width, size.height) / 2f

            angles.forEachIndexed { index, sweepAngle ->
                // Animate radius for selected part
                val scaleFactor = animatedScales[index]
                val scaledRadius = radius * scaleFactor

                // Draw each slice
                drawArc(
                    color = colors[index],
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = Offset(
                        (size.width - scaledRadius * 2) / 2,
                        (size.height - scaledRadius * 2) / 2
                    ),
                    size = Size(scaledRadius * 2, scaledRadius * 2),
                    style = Stroke(width = radius * 0.3f)
                )

                startAngle += sweepAngle
            }
        }

        // Central Text
        Text(
            text = centerText,
            color = Color.Black,
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// Helper functions (implement these if not already defined)
fun calculateTouchAngle(center: Offset, tapOffset: Offset): Float {
    val deltaX = tapOffset.x - center.x
    val deltaY = tapOffset.y - center.y
    var angle = atan2(deltaY, deltaX).toDegrees()
    if (angle < 0) angle += 360f
    return angle
}

fun findHitIndex(touchAngle: Float, angles: List<Float>): Int {
    var cumulativeAngle = 0f
    angles.forEachIndexed { index, sweepAngle ->
        cumulativeAngle += sweepAngle
        if (touchAngle <= cumulativeAngle) return index
    }
    return -1
}

// Extension function to convert radians to degrees
fun Float.toDegrees(): Float = this * 180f / PI.toFloat()

@Composable
fun AnimatedCircleChartScreen(
    viewModel: PieChartViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val totalAmount by viewModel.totalAmount.collectAsState()
    val parts by viewModel.listForms.collectAsState()


    val partNames = listOf("Conteo/Libre/Transectos",  "Clima", "Cobertura", "Fotos", "Trampa", "Vegetación")

    val colors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.secondaryContainer,
        MaterialTheme.colorScheme.inversePrimary,
        MaterialTheme.colorScheme.tertiaryContainer,
    )

    if (parts.isNotEmpty()) {
        AnimatedCircleChart(
            totalAmount = totalAmount.toFloat(),
            parts = parts,
            partNames = partNames,
            colors = colors
        )
    } else {
        CircularProgressIndicator()
    }
}



@Preview(showBackground = true)
@Composable
fun CircleChartPreview() {
    LechendasAppTheme {
       AnimatedCircleChartScreen()
    }
}
