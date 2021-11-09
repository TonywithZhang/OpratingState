package com.minghua.opratingstate.models

data class ProjectInformationModel(
    val name: String,
    val radiation: Int,
    val equationHours: Int,
    val production: Int,
    val radiationTime: Float,
    val pr: Float,
    val converters: List<ConverterStatusModel>
)

data class ConverterStatusModel(
    val name: String,
    val efficiency: Float,
    val streamBoxState: List<StreamStatusModel>,
    val startTime : String,
    val quitTime : String
)

data class StreamStatusModel(val name: String, val healthPointers: List<Int>, val state : String)

