package com.minghua.opratingstate.models

data class LocalRoofStateModel(
    val lineData: List<LineData>,
    val times: List<String>,
    val title: String
)
data class LocalRoofSum(val state: List<LocalRoofStateModel>,val p1: Long,val p2: Long)