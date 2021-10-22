package com.minghua.opratingstate.ui.fragments

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.minghua.opratingstate.R
import com.minghua.opratingstate.models.BottomNavigationModel

val bottomNavigationModels = listOf(
    BottomNavigationModel("首页", R.drawable.baseline_home_black_24dp, "main"),
    BottomNavigationModel("日志", R.drawable.baseline_add_chart_black_24dp, "log"),
    BottomNavigationModel("消息", R.drawable.baseline_message_black_24dp, "message"),
    BottomNavigationModel("我的", R.drawable.baseline_account_circle_black_24dp, "user")
)

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalAnimationApi
@Composable
fun PropertyNavigation() {
    var selectedItem by remember {
        mutableStateOf(0)
    }
    val navController = rememberAnimatedNavController()
    Scaffold(modifier = Modifier
        .fillMaxSize(),
        bottomBar = {
            BottomNavigation(
                modifier = Modifier
                    .fillMaxWidth(),
                backgroundColor = Color(0xffF2F2F2)
            ) {
                bottomNavigationModels.forEachIndexed { index, model ->
                    BottomNavigationItem(
                        selected = selectedItem == index,
                        onClick = { selectedItem = index;navController.navigate(model.link) },
                        icon = {
                            Image(
                                painter = painterResource(id = model.iconId),
                                contentDescription = null
                            )
                        }, label = { Text(text = model.name) },
                        alwaysShowLabel = false
                    )
                }
            }
        }) {

        AnimatedNavHost(navController = navController, startDestination = "main") {
            composable("main",enterTransition = {
                initial, target ->
                EnterTransition.None
            },exitTransition = {
                initial, target ->
                ExitTransition.None
            }) {
                LandScape(userName = "长阳创谷E栋屋顶",navController = navController)
            }
            composable("log",enterTransition = {
                    initial, target ->
                EnterTransition.None
            },exitTransition = {
                    initial, target ->
                ExitTransition.None
            }) {
                LogPage()
            }
            composable("message",enterTransition = {
                    initial, target ->
                EnterTransition.None
            },exitTransition = {
                    initial, target ->
                ExitTransition.None
            }) {
                MessagePage()
            }
            composable("user",enterTransition = {
                    initial, target ->
                EnterTransition.None
            },exitTransition = {
                    initial, target ->
                ExitTransition.None
            }){
                UserPage()
            }
            composable("detailItem/{name}",enterTransition = {
                    initial, target ->
                EnterTransition.None
            },exitTransition = {
                    initial, target ->
                ExitTransition.None
            }){
                ProjectInformation(navController,it.arguments?.getString("name") ?: "长阳创谷E栋屋顶")
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun PropertyNavigationPreview() {
    PropertyNavigation()
}