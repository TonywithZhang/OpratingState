package com.minghua.opratingstate.models

data class ConverterSampleModel(
    val name: String,
    val sampleNumber: Int,
    val efficiency: Float,
    val efficiency25: Float,
    val efficiency50: Float,
    val efficiency100: Float
)
