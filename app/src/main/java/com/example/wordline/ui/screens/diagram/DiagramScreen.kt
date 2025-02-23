package com.example.wordline.ui.screens.diagram

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DiagramScreen(modifier: Modifier = Modifier, viewModel: DiagramViewModel = viewModel()) {
    val density = LocalDensity.current
    var boxHeight by remember { mutableStateOf(0.dp) }
    val minCardHeight = 40.dp
    var cardHeight by remember { mutableStateOf(minCardHeight) }
    val velocityTracker = remember { VelocityTracker() }
    var prevCardHeight = remember { minCardHeight }
    var cardExpanded = remember { false }
    lateinit var layoutCoordinates: LayoutCoordinates

    Box(modifier = Modifier
        .fillMaxSize()
        .onSizeChanged { size ->
            boxHeight = with(density) { size.height.toDp() }
        }
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)
                .onSizeChanged { size ->
                    viewModel.updateCanvasSize(size.toSize())
                }
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        viewModel.onLineTapped(offset)
                    }
                }
        ) {
            drawLine(
                start = Offset(x = size.width, y = 0f),
                end = Offset(x = 0f, y = size.height),
                color = Color.Red,
                strokeWidth = 5f
            )
        }
        Card(
            elevation = CardDefaults.cardElevation(0.dp),
            shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(cardHeight)
                .onGloballyPositioned { layoutCoordinates = it }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            if (with(density) { offset.y.toDp() } > minCardHeight) return@detectDragGestures
                            velocityTracker.resetTracking()
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            velocityTracker.addPosition(
                                change.uptimeMillis,
                                layoutCoordinates.localToScreen(change.position)
                            )
                            cardHeight = maxOf(
                                cardHeight - with(density) { dragAmount.y.toDp() },
                                minCardHeight
                            )
                        },
                        onDragEnd = {
                            val velocity = velocityTracker.calculateVelocity()
                            val flickVelocity = 800f
                            if (velocity.y > flickVelocity) cardHeight = minCardHeight
                            else if (velocity.y < flickVelocity * -1 && !cardExpanded && prevCardHeight != minCardHeight) {
                                cardHeight = prevCardHeight
                            }
                            cardExpanded = cardHeight != minCardHeight
                            if (cardExpanded) prevCardHeight = cardHeight
                        }
                    )
                }
                .align(Alignment.BottomCenter)
        ) {
            LineSettings()
        }
    }
}


@Composable
fun LineSettings(modifier: Modifier = Modifier) {
    Text(modifier = modifier, text = "Placeholder")
}


@Preview
@Composable
private fun DiagramScreenPreview() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        DiagramScreen(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}