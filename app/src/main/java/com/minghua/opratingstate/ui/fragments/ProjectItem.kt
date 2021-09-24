package com.minghua.opratingstate.ui.fragments

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.minghua.opratingstate.R

@Composable
fun ProjectItem(@DrawableRes iconId: Int,name: String,modifier: Modifier = Modifier) {
    Column(modifier = modifier,horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.padding(5.dp)
        )
        Text(text = name)
    }
}