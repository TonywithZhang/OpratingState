package com.minghua.opratingstate.ui.fragments.project_information

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.ButtonDefaults.elevation
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
import com.minghua.opratingstate.ui.fragments.ProjectItem

@Composable
fun PowerInformation(controller: NavHostController?) {
    Card(
        elevation = 0.dp,
        shape = RoundedCornerShape(5),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.618f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column {
                    ProjectItem(
                        iconId = R.drawable.ic_device,
                        name = "PV1",
                        modifier = Modifier.width(60.dp)
                    )
                    ProjectItem(
                        iconId = R.drawable.ic_device,
                        name = "PV2",
                        modifier = Modifier.width(60.dp)
                    )
                }
                LinkToImage()
                Image(
                    painter = painterResource(id = R.drawable.ic_converter),
                    contentDescription = null,
                    Modifier.size(80.dp)
                )
                LinkToImage()
                Image(
                    painter = painterResource(id = R.drawable.ic_net),
                    contentDescription = null,
                    Modifier.size(100.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentWidth()
                    .align(Alignment.TopEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10))
                        .background(Color.LightGray)
                        .padding(horizontal = 5.dp, vertical = 3.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cc_bell_o),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.Red
                    )
                    Text(text = "1")
                }
                Button(
                    onClick = { controller?.navigate("topological/长阳创谷E栋屋顶") },
                    colors = buttonColors(backgroundColor = Color.Transparent),
                    elevation = elevation(0.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_more),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Text(
                text = "当前功率2400W",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 3.dp),
                style = MaterialTheme.typography.h5
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPowerInformation() {
    Surface {
        PowerInformation(null)
    }
}