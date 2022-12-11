package ru.startandroid.develop.scrambler.Modules.General.View

import android.graphics.Bitmap

interface GeneralGridAdapterDelegate {
    fun cellsCount(): Int
    fun imageForCell(index: Int): Bitmap
}