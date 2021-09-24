package com.minghua.opratingstate.ui.fragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.minghua.opratingstate.R
import com.minghua.opratingstate.utils.items

@Composable
fun LandScape(userName: String = "") {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(scrollState, orientation = Orientation.Vertical)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(0xffA8D08D)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "资产概览", style = MaterialTheme.typography.h5)
        }
        FlowRow(modifier = Modifier.fillMaxWidth(),mainAxisAlignment = FlowMainAxisAlignment.SpaceBetween) {
            items.forEachIndexed loop@{ index, itemModel ->
                if (index >= 7) return@loop
                ProjectItem(
                    iconId = itemModel.iconId,
                    name = itemModel.name,
                    modifier = Modifier.fillMaxWidth(0.22f)
                )
            }
            if (items.size >= 8)
                Button(
                    onClick = {},
                    colors = buttonColors(backgroundColor = Color.Transparent),
                    elevation = elevation(0.dp)
                ) {
                    ProjectItem(
                        iconId = R.drawable.ic_more,
                        name = "更多",
                        modifier = Modifier.fillMaxWidth(0.22f)
                    )
                }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LandScapePreview() {
    LandScape()
}