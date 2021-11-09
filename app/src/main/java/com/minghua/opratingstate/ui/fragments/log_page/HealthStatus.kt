package com.minghua.opratingstate.ui.fragments.log_page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun HealthStatus() {
    Column(modifier = Modifier.fillMaxWidth()) {
        projects.forEach {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                Text(text = it.name, style = TextStyle(fontWeight = FontWeight.Bold))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
                )
                {
                    it.converters.forEach {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            var converterExpanded by remember { mutableStateOf(true) }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = if (converterExpanded) R.drawable.ic_folded else R.drawable.ic_expanded),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(18.dp)
                                        .clickable { converterExpanded = !converterExpanded }
                                )
                                LogPageItem(
                                    imageId = R.drawable.ic_converter_efficiency,
                                    title = it.name,
                                    value = String.format("%.2f%%", it.efficiency * 100),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                            AnimatedVisibility(visible = converterExpanded) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 10.dp, top = 5.dp, bottom = 5.dp)
                                ) {
                                    it.streamBoxState.forEach {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            var streamExpanded by remember { mutableStateOf(true) }
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(
                                                    painter = painterResource(id = if (streamExpanded) R.drawable.ic_folded else R.drawable.ic_expanded),
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .size(18.dp)
                                                        .clickable {
                                                            streamExpanded = !streamExpanded
                                                        }
                                                )
                                                LogPageItem(
                                                    imageId = R.drawable.ic_stream_box,
                                                    title = it.name,
                                                    value = it.state,
                                                    modifier = Modifier.fillMaxWidth()
                                                )
                                            }
                                            AnimatedVisibility(visible = streamExpanded) {
                                                FlowRow(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(start = 10.dp),
                                                    mainAxisAlignment = FlowMainAxisAlignment.Start,
                                                    crossAxisSpacing = 5.dp
                                                ) {
                                                    it.healthPointers.forEachIndexed { index, i ->
                                                        LogPageItem(
                                                            imageId = R.drawable.photovoltaic_panel,
                                                            title = "光伏组串${index}健康指数",
                                                            value = i.toString(),
                                                            modifier = Modifier
                                                                .fillMaxWidth(0.45f)
                                                                .padding(horizontal = 10.dp)
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHealthStatus() {
    HealthStatus()
}