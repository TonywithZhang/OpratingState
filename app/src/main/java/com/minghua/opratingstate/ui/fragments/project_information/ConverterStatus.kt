package com.minghua.opratingstate.ui.fragments.project_information

import android.hardware.camera2.params.OisSample
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minghua.opratingstate.ui.drawings.ProgressCircle
import com.minghua.opratingstate.ui.theme.stateColors
import com.minghua.opratingstate.ui.theme.topBarColor

@Composable
fun ConverterStatus(
    progress: Float = 0f,
    converterName: String,
    sampleNumber: Int,
    efficiency25: Float,
    efficiency50: Float,
    efficiency100: Float
) {
    Card(
        elevation = 3.dp, shape = RoundedCornerShape(5), modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth()
            .padding(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            ProgressCircle(progress = progress, modifier = Modifier.size(55.dp), showText = true,color = topBarColor)
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.padding(10.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = converterName,
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "有效样本数：$sampleNumber",
                        style = TextStyle(fontSize = 12.sp, color = Color.Gray)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = String.format("%.2f%%", efficiency25 * 100),
                        modifier = Modifier
                            .border(
                                1.dp,
                                stateColors[0],
                                RoundedCornerShape(15)
                            )
                            .padding(3.dp),
                        style = TextStyle(color = stateColors[0])
                    )
                    Text(
                        text = String.format("%.2f%%", efficiency50 * 100),
                        modifier = Modifier
                            .border(
                                1.dp, stateColors[1],
                                RoundedCornerShape(15)
                            )
                            .padding(3.dp),
                        style = TextStyle(color = stateColors[1])
                    )
                    Text(
                        text = String.format("%.2f%%", efficiency100 * 100),
                        modifier = Modifier
                            .border(
                                1.dp, stateColors[2],
                                RoundedCornerShape(15)
                            )
                            .padding(3.dp),
                        style = TextStyle(color = stateColors[2])
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConverterStatus() {
    ConverterStatus(
        progress = 0.9790f,
        converterName = "1区A逆变器",
        sampleNumber = 2668,
        efficiency25 = 0.9778f,
        efficiency50 = 0.9845f,
        efficiency100 = 0.9828f
    )
}