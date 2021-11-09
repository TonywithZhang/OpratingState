package com.minghua.opratingstate.ui.fragments.log_page

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.minghua.opratingstate.R
import com.minghua.opratingstate.utils.projects

@Composable
fun ConverterStateInformation() {
    Column(modifier = Modifier.fillMaxWidth()) {
        projects.forEach {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                Text(text = it.name, style = TextStyle(fontWeight = FontWeight.Bold))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    mainAxisAlignment = FlowMainAxisAlignment.SpaceAround
                ) {
                    it.converters.forEach {
                        Column(
                            modifier = Modifier.fillMaxWidth(0.4f),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(5.dp),
                                verticalAlignment = CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_converter_efficiency),
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp),
                                    tint = Color(0xFF1296db)
                                )
                                Text(text = it.name)
                            }
                            LogPageItem(
                                imageId = R.drawable.ic_start,
                                title = "并网时间：",
                                value = it.startTime,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp)
                            )
                            LogPageItem(
                                imageId = R.drawable.ic_quit,
                                title = "离网时间：",
                                value = it.quitTime,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConverterStateInformation() {
    ConverterStateInformation()
}