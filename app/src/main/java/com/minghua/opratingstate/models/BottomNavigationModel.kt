package com.minghua.opratingstate.models

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable


data class BottomNavigationModel(val name: String,@DrawableRes val iconId: Int,val link: String)
