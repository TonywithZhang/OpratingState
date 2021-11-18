package com.minghua.opratingstate.models

import com.madrapps.plot.line.DataPoint

data class OutputRadiationModel(val times : List<String>,val dataList : Array<List<DataPoint>>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OutputRadiationModel

        if (times != other.times) return false
        if (!dataList.contentEquals(other.dataList)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = times.hashCode()
        result = 31 * result + dataList.contentHashCode()
        return result
    }
}
