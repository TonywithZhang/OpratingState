package com.minghua.opratingstate.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.minghua.opratingstate.BuildConfig
import com.minghua.opratingstate.ui.fragments.log_page.ConverterStateInformation
import com.minghua.opratingstate.ui.fragments.log_page.HealthStatus
import com.minghua.opratingstate.ui.fragments.log_page.SummaryReport
import com.minghua.opratingstate.ui.theme.topBarColor
import com.minghua.opratingstate.utils.ExcelUtils
import com.minghua.opratingstate.utils.logTypes
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.listItems
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import com.vanpra.composematerialdialogs.title
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.exists

@ExperimentalPermissionsApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LogPage() {

    val activity = LocalContext.current
    //文件分享
    fun shareFile() {
//        Log.d(TAG, "shareFile: 权限请求之前")
//        if (ActivityCompat.checkSelfPermission(
//                activity,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(activity,arrayOf(Manifest.permission_group.STORAGE),1)
//        }else{
//            Log.d(TAG, "shareFile: 权限已经获取")
//        }
        CoroutineScope(Dispatchers.IO).launch {
            var filePath: Path = Paths.get("")
            kotlin.runCatching {
                val file = activity.assets.open("template.xlsx")
                filePath = Paths.get(activity.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.path, "运行日志.xlsx")
                if (Files.exists(filePath)) {
                    Files.delete(filePath)
                }
                Files.copy(file, filePath)
                ExcelUtils.modifyExportFile(filePath)
            }

            withContext(Dispatchers.Main){
                if (filePath.exists()) {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        val contentUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".fileprovider",filePath.toFile());
                        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } else {
                        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(filePath.toFile()));
                    }
                    shareIntent.type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";//此处可发送多种文件
                    shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    activity.startActivity(Intent.createChooser(shareIntent, "分享文件"));
                } else {
                    Toast.makeText(activity, "分享文件不存在", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    var title by remember { mutableStateOf("综合报表") }
    val dialogState = rememberMaterialDialogState()
    MaterialDialog(dialogState = dialogState) {
        title("请选择：")
        listItems(list = logTypes, onClick = { _, item -> title = item })
    }
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(topBarColor)
            ) {
                Text(
                    text = "日志",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        item {
            Button(
                onClick = { dialogState.show() },
                colors = buttonColors(backgroundColor = Color.Transparent),
                elevation = elevation(0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = title)
                    Icon(Icons.Filled.ArrowForward, contentDescription = null)
                }
            }
        }
        when (title) {
            "综合报表" -> item { SummaryReport() }
            "设备健康指数报表" -> item { HealthStatus() }
            else -> item { ConverterStateInformation() }
        }

        item {
            Spacer(modifier = Modifier.height(50.dp))
        }
        item {
            Button(
                onClick = { shareFile() }, modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .height(35.dp), colors = buttonColors(backgroundColor = topBarColor)
            ) {
                Text(
                    text = "导出日志",
                    style = TextStyle(
                        color = Color.White,
                        background = topBarColor,
                        fontSize = 16.sp
                    )
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }


}

@ExperimentalPermissionsApi
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun LogPagePreview() {
    LogPage()
}