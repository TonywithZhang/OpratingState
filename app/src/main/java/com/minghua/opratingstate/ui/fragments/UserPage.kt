package com.minghua.opratingstate.ui.fragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minghua.opratingstate.ui.theme.topBarColor
import com.minghua.opratingstate.R

@Composable
fun UserPage() {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xfff2f2f2))) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(topBarColor)
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.ic_user_icon),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "用户名",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.ic_setting),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )

        }
        //使用指南
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.padding(5.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.ic_instruction),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "使用指南",style = MaterialTheme.typography.h5)
            }
            Icon(Icons.Filled.ArrowForward, contentDescription = null,modifier =Modifier.size(30.dp))
        }
        //收收益测算
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.padding(5.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.ic_bill),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "收益测算",style = MaterialTheme.typography.h5)
            }
            Icon(Icons.Filled.ArrowForward, contentDescription = null,modifier =Modifier.size(30.dp))
        }
        //场站设置
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.padding(5.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.ic_place),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "场站设置",style = MaterialTheme.typography.h5)
            }
            Icon(Icons.Filled.ArrowForward, contentDescription = null,modifier =Modifier.size(30.dp))
        }
    }
}

@Preview
@Composable
fun UserPagePreview() {
    UserPage()
}