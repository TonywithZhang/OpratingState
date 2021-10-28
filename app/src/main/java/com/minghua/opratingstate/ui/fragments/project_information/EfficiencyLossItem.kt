package com.minghua.opratingstate.ui.fragments.project_information

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minghua.opratingstate.R
import com.minghua.opratingstate.ui.theme.stateColors

@Composable
fun EfficiencyLossItem(name: String, time: Int, loss: Float, advisedDate: String) {
    Card(
        elevation = 5.dp, shape = RoundedCornerShape(5), modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_device),
                contentDescription = null,
                modifier = Modifier
                    .height(50.dp)
                    .width(81.dp)
                    .clip(
                        RoundedCornerShape(10)
                    )
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp)
            ) {
                Text(text = name, style = TextStyle(fontWeight = FontWeight.Bold))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$time 天",
                        style = TextStyle(color = stateColors[0]),
                        modifier = Modifier
                            .border(1.dp, stateColors[0], RoundedCornerShape(10))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                    Text(
                        text = String.format("%.2f%%",loss),
                        style = TextStyle(color = stateColors[1]),
                        modifier = Modifier
                            .border(1.dp, stateColors[1], RoundedCornerShape(10))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                    Text(
                        text = advisedDate,
                        style = TextStyle(color = stateColors[2]),
                        modifier = Modifier
                            .border(1.dp, stateColors[2], RoundedCornerShape(10))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEfficiencyLossItem() {
    EfficiencyLossItem("光伏组串1", 5, 0.0275f, "2021-11-24")
}