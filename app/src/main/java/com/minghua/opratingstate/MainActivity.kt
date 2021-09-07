package com.minghua.opratingstate

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
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
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.*
import com.minghua.opratingstate.network.repositories.loginNetwork
import com.minghua.opratingstate.ui.fragments.StateSummary
import com.minghua.opratingstate.ui.theme.OpratingStateTheme
import com.minghua.opratingstate.utils.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

val loginKey = booleanPreferencesKey("login_state")

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpratingStateTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainPage()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainPage(modifier: Modifier = Modifier) {
    val loginStateFlow : Flow<Boolean> = LocalContext.current.dataStore.data.map { preference -> preference[loginKey] ?: false }
    var loginState by remember { mutableStateOf(false) }
    LaunchedEffect(0){
        loginStateFlow.collect { loginState = it }
    }
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            if (loginState) StateSummary()
            else LogInView(onStateChange = { loginState = it })
        }
        composable("others") {

        }
    }
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

