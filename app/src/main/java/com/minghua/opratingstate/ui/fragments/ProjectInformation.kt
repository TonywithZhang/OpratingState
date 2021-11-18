package com.minghua.opratingstate.ui.fragments

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.minghua.opratingstate.ui.fragments.project_information.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProjectInformation(controller: NavHostController?,name: String) {
    val scroll = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffF2F2F2))
            .offset(x = 0.dp,y = (-3).dp)
            .verticalScroll(scroll),
        verticalArrangement = Arrangement.spacedBy(3.dp,Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InformationTitle(title = name)
        LocationInformation(location = "中国上海市杨浦区长阳路1687号长阳创谷")
        CapacityInformation()
        WeatherCard()
        PowerInformation(controller)
        EfficiencyCard()
        AssemblyPower()
        ElectricPower(controller,name)
        ConverterEfficiency(controller,name)
        AssemblyEfficiency(controller!!,name)
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewProjectInformation() {
    ProjectInformation(null,"长阳创谷E楼屋顶光伏")
}