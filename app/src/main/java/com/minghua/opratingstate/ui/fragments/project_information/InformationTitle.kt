package com.minghua.opratingstate.ui.fragments.project_information

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.minghua.opratingstate.ui.drawings.DetailProjectItem
import com.minghua.opratingstate.ui.theme.topBarColor
import com.minghua.opratingstate.utils.items
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.listItems
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

@Composable
fun InformationTitle(title: String){
    val dialogState = rememberMaterialDialogState()
    MaterialDialog(dialogState = dialogState) {
        listItems(list = items,onClick = { index, item ->  }) { index, item ->
            DetailProjectItem(model = item)
        }
    }
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(topBarColor)) {
        Text(text = "$title﹀",style = MaterialTheme.typography.h6,modifier = Modifier.clickable { dialogState.show() })
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInformationTitle(){
    InformationTitle(title ="长阳创谷E楼屋顶光伏")
}