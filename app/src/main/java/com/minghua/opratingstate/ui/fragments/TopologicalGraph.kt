package com.minghua.opratingstate.ui.fragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minghua.opratingstate.R
import com.minghua.opratingstate.ui.theme.topBarColor

@Composable
fun TopologicalGraph(name: String){
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(35.dp)
            .background(topBarColor)) {
            Text(text = "屋顶电站拓扑",modifier = Modifier.align(Alignment.Center))
        }
        Image(painter = painterResource(id = R.drawable.topological), contentDescription = null,modifier = Modifier.fillMaxWidth(),contentScale = ContentScale.FillWidth)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopologicalGraph(){
    Surface {
        TopologicalGraph("长阳创谷E栋屋顶")
    }
}