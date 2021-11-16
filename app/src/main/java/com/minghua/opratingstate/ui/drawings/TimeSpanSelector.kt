package com.minghua.opratingstate.ui.drawings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.BottomNavigationDefaults.Elevation
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material.DrawerDefaults.Elevation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minghua.opratingstate.R

@Composable
fun TimeSpanSelector(timeSpan: String, forward: () -> Unit,enable :Boolean = true, backward: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp), horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = backward,
            shape = CircleShape,
            border = BorderStroke(1.dp, Color.Black),
            colors = buttonColors(backgroundColor = Color.Transparent, contentColor = Color.Black),
            modifier = Modifier
                .height(18.dp)
                .width(18.dp),
            contentPadding = PaddingValues(all = 2.dp),
            enabled = enable,
            elevation = elevation(0.dp)
        ) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = null
            )
        }
//        Image(
//            painter = painterResource(id = R.drawable.ic_backward),
//            contentDescription = null,
//            Modifier
//                .height(15.dp)
//                .width(15.dp)
//                .padding(top = 2.dp)
//        )
        Text(text = timeSpan, Modifier.padding(horizontal = 25.dp))
        Button(
            onClick = forward,
            shape = CircleShape,
            border = BorderStroke(1.dp, Color.Black),
            colors = buttonColors(backgroundColor = Color.Transparent, contentColor = Color.Black),
            modifier = Modifier
                .height(18.dp)
                .width(18.dp),
            contentPadding = PaddingValues(all = 2.dp),
            enabled = enable,
            elevation = elevation(0.dp)
        ) {
            Icon(
                Icons.Filled.ArrowForward,
                contentDescription = null
            )
//            Image(
//                painter = painterResource(id = R.drawable.ic_forward),
//                contentDescription = null,
//                Modifier
//                    .height(15.dp)
//                    .width(15.dp)
//            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTimeSpanSelector() {
    androidx.compose.material.Surface() {
        TimeSpanSelector(timeSpan = "2021-09-05 ~ 2021-09-11", forward = {}, backward = {})
    }
}