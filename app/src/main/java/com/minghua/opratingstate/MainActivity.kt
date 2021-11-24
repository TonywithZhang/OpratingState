package com.minghua.opratingstate

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.airbnb.lottie.compose.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.minghua.opratingstate.network.repositories.loginNetwork
import com.minghua.opratingstate.ui.fragments.PropertyNavigation
import com.minghua.opratingstate.ui.theme.OpratingStateTheme
import com.minghua.opratingstate.ui.theme.topBarColor
import com.minghua.opratingstate.utils.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

val loginKey = booleanPreferencesKey("login_state")

class MainActivity : ComponentActivity() {
    //    private val permissionRequest = registerForActivityResult(ActivityResultContracts.RequestPermission()){
//        if (it){
//            Toast.makeText(this,"权限请求成功",Toast.LENGTH_LONG).show()
//        }
//        else{
//            Toast.makeText(this,"权限请求失败",Toast.LENGTH_LONG).show()
//        }
//    }
    @ExperimentalPermissionsApi
    @ExperimentalAnimationApi
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window,false)
        setContent {
            OpratingStateTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainPage()
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val controller = window.insetsController

            if(controller != null) {
                controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else{
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
        //window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        //window.insetsController?.hide(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
        //window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        permissionRequest.launch(Manifest.permission_group.STORAGE)
    }

    private var pressTime: Long = 0L
    override fun onBackPressed() {
        super.onBackPressed()
        if (System.currentTimeMillis() - pressTime < 2000) finish()
        pressTime = System.currentTimeMillis()
    }
}

@ExperimentalPermissionsApi
@SuppressLint("FlowOperatorInvokedInComposition")
@ExperimentalAnimationApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainPage(modifier: Modifier = Modifier.background(topBarColor)) {
    val loginStateFlow: Flow<Boolean> =
        LocalContext.current.dataStore.data.map { preference -> preference[loginKey] ?: false }
    var loginState by remember { mutableStateOf(false) }
    LaunchedEffect(0) {
        loginStateFlow.collect { loginState = it }
    }
    if (loginState) PropertyNavigation()
    else LogInView(onStateChange = { loginState = it })
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LogInView(onStateChange: (Boolean) -> Unit) {
    Box(modifier = Modifier.background(Color(0xFFEFEEEE))) {
        ComposeLottie()
        InputForm(onStateChange)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InputForm(onStateChange: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(285.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEFEEEE)), verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(text = "请登录", style = MaterialTheme.typography.h3)
                }
                Spacer(modifier = Modifier.height(25.dp))
                var isUserNameFocused by remember { mutableStateOf(false) }
                val userNameRadius = animateIntAsState(
                    targetValue = if (isUserNameFocused) 0 else 10,
                    animationSpec = tween(durationMillis = 1500)
                )
                val userNameColor = animateColorAsState(
                    targetValue = if (isUserNameFocused) Color.Gray else Color(0xFFEFEEEE),
                    animationSpec = tween(durationMillis = 1500)
                )
                TextField(
                    value = userName, onValueChange = { userName = it }, modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .padding(horizontal = 30.dp)
                        .drawColoredShadow(
                            Color.Black,
                            0.1f,
                            borderRadius = 25.dp,
                            shadowRadius = 10.dp,
                            offsetX = userNameRadius.value.dp,
                            offsetY = userNameRadius.value.dp
                        )
                        .drawColoredShadow(
                            Color.White,
                            0.9f,
                            borderRadius = 25.dp,
                            shadowRadius = 10.dp,
                            offsetX = (-userNameRadius.value).dp,
                            offsetY = (-userNameRadius.value).dp
                        )
                        .onFocusChanged { isUserNameFocused = it.isFocused },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_username),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(2.dp)
                                .size(35.dp)
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = userNameColor.value,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(corner = CornerSize(50)),
                    placeholder = {
                        Text(text = "请输入用户名")
                    }
                )
                Spacer(modifier = Modifier.height(25.dp))
                var isPasswordFocused by remember {
                    mutableStateOf(false)
                }
                val passwordAnimation = animateIntAsState(
                    targetValue = if (isPasswordFocused) 0 else 10,
                    animationSpec = tween(durationMillis = 1500)
                )
                val passwordColor = animateColorAsState(
                    targetValue = if (isPasswordFocused) Color.Gray else Color(0xFFEFEEEE),
                    animationSpec = tween(durationMillis = 1500)
                )
                TextField(
                    value = password, onValueChange = { password = it }, modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .padding(horizontal = 30.dp)
                        .drawColoredShadow(
                            Color.Black,
                            0.1f,
                            borderRadius = 25.dp,
                            shadowRadius = 10.dp,
                            offsetX = passwordAnimation.value.dp,
                            offsetY = passwordAnimation.value.dp
                        )
                        .drawColoredShadow(
                            Color.White,
                            0.9f,
                            borderRadius = 25.dp,
                            shadowRadius = 10.dp,
                            offsetX = (-passwordAnimation.value).dp,
                            offsetY = (-passwordAnimation.value).dp
                        )
                        .onFocusChanged { isPasswordFocused = it.isFocused },
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_password),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(2.dp)
                                .size(35.dp)
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = passwordColor.value,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(corner = CornerSize(50)),
                    placeholder = {
                        Text(text = "请输入密码")
                    },
                    visualTransformation = PasswordVisualTransformation()
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                verticalArrangement = Arrangement.Center,
            ) {
                val context = LocalContext.current
                Button(
                    onClick = {
                        try {
                            val scope = CoroutineScope(Dispatchers.Main)
                            scope.launch {
                                withContext(Dispatchers.IO) {
                                    val network = loginNetwork()
                                    val result = network.login(userName, password)
                                    withContext(Dispatchers.Main) {
                                        if (result.msg == "ok") {
                                            onStateChange(true)
                                            context.dataStore.edit { settings ->
                                                settings[loginKey] = true
                                            }
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "账号或者密码错误！",
                                                Toast.LENGTH_LONG
                                            )
                                                .show()
                                        }
                                    }

                                }
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "登录出现错误！", Toast.LENGTH_LONG).show()
                        }

                    },
                    shape = RoundedCornerShape(20),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .shadow(5.dp, shape = RoundedCornerShape(20)),
                    enabled = userName.isNotBlank() && password.isNotBlank()
                ) {
                    Text(text = "点击登录")
                }
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OpratingStateTheme {
        LogInView(onStateChange = {})
    }
}

@Composable
fun ComposeLottie(modifier: Modifier = Modifier) {
    val isPlaying by remember {
        mutableStateOf(true)
    }

// for speed
    val speed by remember {
        mutableStateOf(1f)
    }
    val composition by rememberLottieComposition(

        LottieCompositionSpec
            // here `code` is the file name of lottie file
            // use it accordingly
            .RawRes(R.raw.login_screen)
    )

    // to control the animation
    val progress by animateLottieCompositionAsState(
        // pass the composition created above
        composition,

        // Iterates Forever
        iterations = LottieConstants.IterateForever,

        // pass isPlaying we created above,
        // changing isPlaying will recompose
        // Lottie and pause/play
        isPlaying = isPlaying,

        // pass speed we created above,
        // changing speed will increase Lottie
        speed = speed,

        // this makes animation to restart
        // when paused and play
        // pass false to continue the animation
        // at which is was paused
        restartOnPlay = false

    )

    // Column Composable
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        LottieAnimation(
            composition,
            progress,
            modifier = Modifier
                .fillMaxWidth()
                .scale(1.2f)
                .offset(0.dp, (-220).dp)
                .background(Color(0xFFEFEEEE))
        )
    }
}

