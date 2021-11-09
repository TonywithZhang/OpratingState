package com.minghua.opratingstate.ui.fragments.project_information

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minghua.opratingstate.ui.theme.topBarColor

@Composable
fun EfficiencyCard() {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5))
            .background(Color.White)
            .padding(vertical = 10.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(
                text = "宏观指标", modifier = Modifier
                    .background(
                        topBarColor, shape = RoundedCornerShape(15)
                    )
                    .padding(horizontal = 10.dp, vertical = 2.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(text = "系统效率：90%")
            Text(text = "系统能效：90%")
            Text(text = "电气效率：90%")
        }
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(
                text = "微观指标", modifier = Modifier
                    .background(
                        topBarColor, shape = RoundedCornerShape(15)
                    )
                    .padding(horizontal = 10.dp, vertical = 2.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(text = "光伏组件转换效率：17%")
            Text(text = "逆变器转换能效：95%")
            Text(text = "变压器效率：95%")
        }
    }
}

@Preview
@Composable
fun PreviewEfficiencyCard() {
    Surface {
        EfficiencyCard()
    }
}

