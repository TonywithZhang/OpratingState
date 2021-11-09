package com.minghua.opratingstate.utils

import android.content.ContentValues.TAG
import android.util.Log
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.nio.file.Path
import java.util.*
import kotlin.io.path.inputStream
import kotlin.io.path.outputStream

object ExcelUtils {
    fun modifyExportFile(file: Path) {
        try {
            val workbook = XSSFWorkbook(file.inputStream())
            val sheet = workbook.getSheetAt(0)
            //修改日期
            var addedCount = 0
            sheet.getRow(2).getCell(0).setCellValue(dateFormatter.format(Date()))
            projects.forEachIndexed { index, projectInformationModel ->
                val row =
                    if (index > 4) {
                        sheet.shiftRows(18 + addedCount, 40 + addedCount, 1, true, true)
                        addedCount++
                        sheet.createRow(12)
                    } else sheet.getRow(7 + index)
                row.getCell(0).setCellValue(projectInformationModel.name)
                row.getCell(5).setCellValue(projectInformationModel.radiation.toDouble())
                row.getCell(11).setCellValue(projectInformationModel.production.toDouble())
                row.getCell(14).setCellValue(projectInformationModel.equationHours.toDouble())
                row.getCell(18).setCellValue(projectInformationModel.pr.toDouble())
                row.getCell(21).setCellValue(projectInformationModel.radiationTime.toDouble())
            }
            val totalRemoveCount =
                projects.sumOf { informationModel -> informationModel.converters.sumOf { converterStatusModel -> converterStatusModel.streamBoxState.sumOf { it.healthPointers.size } } }
            sheet.shiftRows(18 + addedCount, 40 + addedCount, totalRemoveCount, true, true)
            val style = workbook.createCellStyle()
            style.wrapText = true
            style.alignment = XSSFCellStyle.ALIGN_CENTER
            style.verticalAlignment = XSSFCellStyle.VERTICAL_CENTER
            projects.forEach { informationModel ->
                val projectStartIndex = 17 + addedCount
                var projectNameSet = false
                informationModel.converters.forEach { converterModel ->
                    val converterStartIndex = 17 + addedCount
                    var converterNameSet = false
                    converterModel.streamBoxState.forEach { statusModel ->
                        val startIndex = 17 + addedCount
                        var streamNameSet = false
                        statusModel.healthPointers.forEachIndexed { index, model ->
                            val rowCount = 17 + addedCount
                            val row = sheet.createRow(rowCount)
                            addedCount++
                            repeat(25) {
                                row.createCell(it).cellStyle = style
                            }
                            //光伏组串的编号
                            row.getCell(5).setCellValue((index + 1).toDouble())

                            sheet.addMergedRegion(CellRangeAddress(rowCount, rowCount, 5, 6))
                            //光伏组串的健康指数
                            row.getCell(7).setCellValue(model.toDouble())
                            sheet.addMergedRegion(CellRangeAddress(rowCount, rowCount, 7, 10))
                            if (!projectNameSet) {
                                projectNameSet = true
                                row.getCell(0).setCellValue(informationModel.name)
                            }
                            if (!converterNameSet) {
                                converterNameSet = true
                                row.getCell(17).setCellValue(converterModel.name)
                                row.getCell(21).setCellValue(
                                    String.format(
                                        "%.2f",
                                        converterModel.efficiency * 100
                                    )
                                )
                            }
                            if (!streamNameSet) {
                                streamNameSet = true
                                row.getCell(11).setCellValue(statusModel.name)
                                row.getCell(13).setCellValue(statusModel.state)
                            }
                        }
                        //汇流箱名字
                        sheet.addMergedRegion(
                            CellRangeAddress(
                                startIndex,
                                startIndex + statusModel.healthPointers.size - 1,
                                11,
                                12
                            )
                        )
                        //汇流箱状态
                        sheet.addMergedRegion(
                            CellRangeAddress(
                                startIndex,
                                startIndex + statusModel.healthPointers.size - 1,
                                13,
                                16
                            )
                        )
                    }
                    //逆变器的名字
                    val totalRowCount =
                        converterModel.streamBoxState.sumOf { it.healthPointers.size }
                    sheet.addMergedRegion(
                        CellRangeAddress(
                            converterStartIndex,
                            converterStartIndex + totalRowCount - 1,
                            17,
                            20
                        )
                    )
                    //逆变器的效率
                    sheet.addMergedRegion(
                        CellRangeAddress(
                            converterStartIndex,
                            converterStartIndex + totalRowCount - 1,
                            21,
                            24
                        )
                    )
                }
                //电站名字
                val projectRowCount =
                    informationModel.converters.sumOf { info -> info.streamBoxState.sumOf { it.healthPointers.size } }
                sheet.addMergedRegion(
                    CellRangeAddress(
                        projectStartIndex,
                        projectStartIndex + projectRowCount - 1,
                        0,
                        4
                    )
                )
            }
            workbook.write(file.outputStream())
            workbook.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}