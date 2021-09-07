package com.minghua.opratingstate.models

data class LocalRoofStateModel(
    val lineData: List<LineData>,
    val times: List<String>,
    val title: String
)