package com.example.wordline.ui.screens.diagram

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorProperty
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordline.data.model.Line

@Composable
fun DiagramScreen(modifier: Modifier = Modifier, viewModel: DiagramViewModel = viewModel()) {
    val testInstruction = viewModel.getTestInstruction()
    Column(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier
            .weight(4f)
            .fillMaxWidth()
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
                start = testInstruction.start,
                end = testInstruction.end,
                color = testInstruction.color,
                strokeWidth = testInstruction.width
            )
        }
        LineInfo(modifier = Modifier.weight(1f))
    }
}

@Composable
fun LineInfo(modifier: Modifier = Modifier) {
    Text(modifier = modifier, text = "Placeholder")
}


@Preview
@Composable
private fun DiagramScreenPreview() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        DiagramScreen(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding))
    }
}