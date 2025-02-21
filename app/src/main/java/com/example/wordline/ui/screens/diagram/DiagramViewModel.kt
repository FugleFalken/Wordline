package com.example.wordline.ui.screens.diagram

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import com.example.wordline.data.model.DrawInstruction
import com.example.wordline.data.model.Line

class DiagramViewModel() : ViewModel() {

    private val testLine = Line(1f, 0f)
    private var canvasSize: Size = Size.Zero

    // NOTE: For testing purposes. Final should have an observable collection of instructions
    fun getTestInstruction(): DrawInstruction {
        // TODO: Convert testline to instruction

    }

    fun updateCanvasSize(size: Size){
        canvasSize = size
    }

    fun onLineTapped(offset: Offset) {
        //TODO make lines clickable
    }
}