package com.minghua.opratingstate.ui.fragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.minghua.opratingstate.ui.theme.topBarColor
import com.minghua.opratingstate.utils.dateFormatter
import com.minghua.opratingstate.utils.messageList
import java.util.*

@Composable
fun MessagePage(controller : NavHostController?) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xfff2f2f2)),
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(35.dp)
                    .background(topBarColor)
            ) {
                Text(
                    text = "消息", style = MaterialTheme.typography.h6, modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Text(
                    text = "今日消息",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
                )
            }
        }
        items(messageList.filter { it.time == dateFormatter.format(Date()) }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .clickable { it.read = true;controller?.navigate("messageDetail/1") }
                    .padding(horizontal = 12.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = it.message)
                Row {
                    Image(
                        painter = painterResource(id = if (it.read) com.minghua.opratingstate.R.drawable.white else com.minghua.opratingstate.R.drawable.ic_unread),
                        contentDescription = null,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Text(
                    text = "未处理消息",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
                )
            }
        }
        items(messageList.filter { it.time != dateFormatter.format(Date()) }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .clickable { it.read = true;controller?.navigate("messageDetail/1") }
                    .padding(horizontal = 12.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = it.message)
                Row {
                    Text(text = it.time)
                    Image(
                        painter = painterResource(id = if (it.read) com.minghua.opratingstate.R.drawable.white else com.minghua.opratingstate.R.drawable.ic_unread),
                        contentDescription = null,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }
        }

    }


}

@Preview
@Composable
fun MessagePagePreview() {
    MessagePage(null)
}