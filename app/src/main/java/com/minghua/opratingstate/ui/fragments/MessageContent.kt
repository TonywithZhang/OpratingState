package com.minghua.opratingstate.ui.fragments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minghua.opratingstate.R
import com.minghua.opratingstate.ui.theme.topBarColor
import com.minghua.opratingstate.utils.attachmentList

@Composable
fun MessageContent(messageId: Int) {
    LazyColumn(modifier = Modifier.fillMaxWidth())
    {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = "消息详情",
                    style = TextStyle(color = topBarColor, fontWeight = FontWeight.Bold)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "时间：2021-09-15 14:15:22")
                    Text(text = "类型：预警")
                    Text(text = "内容：**屋顶**汇流箱通信中断")
                    Text(text = "建议措施：检查通信回路")
                    Text(text = "消息状态：已读未闭环")
                }
            }
        }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
                    .padding(horizontal = 10.dp)
            ) {

            }
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "反馈信息",
                    style = TextStyle(color = topBarColor, fontWeight = FontWeight.Bold)
                )
                var suggestion by remember { mutableStateOf("") }
                TextField(
                    value = suggestion,
                    onValueChange = {suggestion = it},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .border(width = 1.dp, color = Color.Black)
                )
                Button(onClick = { /*TODO*/ },colors = buttonColors(backgroundColor = topBarColor)) {
                    Text(text = "上传附件",style = TextStyle(color = Color.White))
                }

            }
        }
        items(attachmentList)
        {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
                Text(text = it)
                Image(painter = painterResource(id = R.drawable.baseline_delete_black_24dp), contentDescription = null,modifier = Modifier.size(15.dp))
            }
        }
        
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMessageContent() {
    MessageContent(1)
}