package com.minghua.opratingstate.ui.fragments.project_information

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.minghua.opratingstate.R
import com.minghua.opratingstate.ui.drawings.LineChart
import com.minghua.opratingstate.ui.theme.topBarColor
import com.minghua.opratingstate.utils.colorGroup
import com.minghua.opratingstate.utils.lineChartData
import com.minghua.opratingstate.utils.times

@Composable
fun ConverterEfficiency(controller: NavHostController?, name: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5))
            .background(Color.White)
            .padding(3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color.Transparent,
                    disabledBackgroundColor = Color.Transparent,
                    disabledContentColor = Color.Transparent
                ),
                enabled = false,
                modifier = Modifier.size(50.dp),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {

            }
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(15))
                    .background(topBarColor)
                    .padding(2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "逆变器效率 A01")
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    contentDescription = null,
                    modifier = Modifier.size(15.dp)
                )
            }

            Button(
                onClick = { controller?.navigate("converterLine/$name") },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_more),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        LineChart(
            times = times,
            color = colorGroup,
            lineChartData,
            chartTitle = "直流输入电压对比",
            legends = listOf("PV1")
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConverterEfficiency() {
    Surface {
        ConverterEfficiency(null, "")
    }
}