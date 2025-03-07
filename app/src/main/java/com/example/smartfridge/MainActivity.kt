package com.example.smartfridge

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState

import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartfridge.ui.theme.Purple40
import com.example.smartfridge.ui.theme.SmartFridgeTheme
import com.example.smartfridge.ui.theme.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Integer.max

import java.util.Calendar

class DataViewModelFactory(val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DataViewModel(application) as T
    }
}

class ShopDataViewModelFactory(val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShopDataViewModel(application) as T
    }
}

class ChartDataViewModelFactory(val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChartDataViewModel(application) as T
    }
}

class MainActivity : ComponentActivity() {
    var name by mutableStateOf("")
    var type by mutableStateOf("")
    var date_of_manufacture by mutableStateOf("")
    var expiration_date by mutableStateOf("")
    var one_weight by mutableStateOf("")
    var proteins by mutableStateOf("")
    var fats by mutableStateOf("")
    var carbohydrates by mutableStateOf("")
    var kcal by mutableStateOf("")
    var type_of_measurement by mutableStateOf("")

    var Max_Chart_Kcal by mutableStateOf(0)

    var FirstOpen by mutableStateOf(0)

    var DateOffset by mutableStateOf(0)

    lateinit var Mainvm: DataViewModel
    lateinit var DataList: List<Data>

    lateinit var Mainsm: ShopDataViewModel
    lateinit var ShopDataList: List<ShopData>

    lateinit var Maincm: ChartDataViewModel
    lateinit var ChartDataList: List<ChartData>

    var Search_Type by mutableStateOf("None_Type")
    var Search_Name by mutableStateOf("None_Name")

    val scanLauncher = registerForActivityResult(ScanContract())

    { result ->
        if (result.contents == null) {
            Toast.makeText(this, "Не удалось отсканировать продукт", Toast.LENGTH_SHORT).show()
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                var Result = result.contents

                name = Result.substring(0, Result.indexOf('>'))
                Result = Result.substring(Result.indexOf('>') + 3, Result.length)
                type = Result.substring(0, Result.indexOf('>'))
                Result = Result.substring(Result.indexOf('>') + 3, Result.length)
                date_of_manufacture = Result.substring(0, Result.indexOf('>'))
                Result = Result.substring(Result.indexOf('>') + 3, Result.length)
                expiration_date = Result.substring(0, Result.indexOf('>'))
                Result = Result.substring(Result.indexOf('>') + 2, Result.length)
                one_weight = Result.substring(0, Result.indexOf(' '))
                Result = Result.substring(Result.indexOf('>') + 2, Result.length)
                proteins = Result.substring(0, Result.indexOf(' '))
                Result = Result.substring(Result.indexOf(' ') + 1, Result.length)
                fats = Result.substring(0, Result.indexOf(' '))
                Result = Result.substring(Result.indexOf(' ') + 1, Result.length)
                carbohydrates = Result.substring(0, Result.indexOf(' '))
                Result = Result.substring(Result.indexOf(' ') + 1, Result.length)
                kcal = Result.substring(0, Result.indexOf('>'))
                Result = Result.substring(Result.indexOf('>') + 3, Result.length)
                type_of_measurement = Result.substring(0, Result.indexOf('>'))

                if (funisExist(name, expiration_date) == false)
                {
                    Mainvm.changeName(name)
                    Mainvm.changeType(type)
                    Mainvm.changeDate_of_manufacture(date_of_manufacture)
                    Mainvm.changeExpiration_date(expiration_date)
                    Mainvm.changeAllWeight(one_weight)
                    Mainvm.changeOneWeight(one_weight)
                    Mainvm.changeProteins(proteins)
                    Mainvm.changeFats(fats)
                    Mainvm.changeCarbohydrates(carbohydrates)
                    Mainvm.changeKcal(kcal)
                    Mainvm.changeQuantity("1")
                    Mainvm.changeType_of_measurement(type_of_measurement)

                    Mainvm.addData()

                    runOnUiThread{Toast.makeText(this@MainActivity, "Продукт сохранён!", Toast.LENGTH_SHORT).show()}
                }
                else
                {
                    Mainvm.updateQuantityPlus(name, expiration_date)
                    Mainvm.updateWeightPlus(name, expiration_date)

                    runOnUiThread{Toast.makeText(this@MainActivity, "Продукт изменён!", Toast.LENGTH_SHORT).show()}
                }

            }

        }


    }
    @Composable
    fun LockScreenOrientation(orientation: Int) {
        val context = LocalContext.current
        DisposableEffect(Unit) {
            val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
            val originalOrientation = activity.requestedOrientation
            activity.requestedOrientation = orientation
            onDispose {
                // restore original orientation when view disappears
                activity.requestedOrientation = originalOrientation
            }
        }
    }

    fun Context.findActivity(): Activity? = when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }

    fun funisExist(name: String, expiration_date: String): Boolean {


        for (i in 0..DataList.size - 1)
        {
            if (DataList[i].name == name && DataList[i].expiration_date == expiration_date) {
                return true
            }

        }
        return false
    }

    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartFridgeTheme{
                LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                TabLayout()

            }

        }
    }

    private fun scan() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        options.setOrientationLocked(false)
        scanLauncher.launch(options)
    }

    @Composable
    fun ShopMain(sm: ShopDataViewModel = viewModel())
    {
        val shopdataList by sm.shopdataList.observeAsState(listOf())

        val message = remember { mutableStateOf(TextFieldValue("")) }
        val messages = remember { mutableStateListOf<String>() }

        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.92f))
        {
            Column{
                Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.1f), contentAlignment = Alignment.Center)
                {
                    Text(text = "Список покупок", fontSize = 30.sp, fontWeight= FontWeight.Bold)
                }

                Column(modifier = Modifier.fillMaxSize()){

                    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.2f).padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
                        TextField(
                            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f),
                            value = message.value,
                            onValueChange = {message.value = it},
                            textStyle = TextStyle(fontSize =  24.sp),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color(0xffeeeeee),
                                unfocusedTextColor = Color(0xff888888),
                                focusedContainerColor = TabColor,
                                focusedTextColor = Color(0xff222222)
                            )
                        )

                        ExtendedFloatingActionButton(
                            icon = {
                                Icon(
                                    Icons.Filled.Add,
                                    contentDescription = "Добавить"
                                )
                            },
                            text = { Text("Добавить") },
                            shape = RoundedCornerShape(5.dp),
                            onClick = {
                                if (message.value.text.isNotEmpty()) {
                                    messages.add(message.value.text)
                                    Mainsm = sm
                                    Mainsm.changeText(message.value.text)
                                    Mainsm.addShopData()
                                    message.value = TextFieldValue("")
                                }
                            },
                            modifier = Modifier.fillMaxSize().padding(top = 10.dp)
                        )
                    }
                    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
                        ShopList(shopdata = shopdataList, sm)
                    }
                }
            }

        }
    }

    @Composable
    fun ShopList(shopdata: List<ShopData>, sm: ShopDataViewModel = viewModel()) {
        ShopDataList = shopdata
        LazyColumn(Modifier.fillMaxWidth().fillMaxHeight()) {

            items(shopdata) {u -> ShopRow(u, sm)}
        }
    }

    fun DeleteShopData(id: Int)
    {
        Mainsm.deleteShopData(id)
    }

    @Composable
    fun ShopRow(shopdata: ShopData, sm: ShopDataViewModel = viewModel()) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier.padding(start = 10.dp, top = 10.dp, end = 10.dp).fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        )
        {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically)
            {
                Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(0.6f)){
                    Text(
                        text = shopdata.text,
                        style = TextStyle(fontSize = 18.sp),
                        color = Color.Black,
                        modifier = Modifier.padding(20.dp)

                    )
                }

                Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    ExtendedFloatingActionButton(
                        icon = { Icon(Icons.Filled.Delete, contentDescription = "Удалить") },
                        text = { Text("Удалить") },
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            Mainsm = sm
                            DeleteShopData(shopdata.id)
                        },
                        modifier = Modifier.padding(10.dp)
                    )
                }

            }
        }
    }

    @Composable
    fun Main(vm: DataViewModel = viewModel()) {
        val dataList by vm.dataList.observeAsState(listOf())

        val State = remember { mutableStateOf("Название") }
        val message = remember { mutableStateOf(TextFieldValue("")) }

        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp

        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.92f))
        {

            Column{
                Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.1f), contentAlignment = Alignment.Center)
                {
                    Text(text = "Главная", fontSize = 30.sp, fontWeight= FontWeight.Bold)
                }

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter)
                {
                    Column(modifier = Modifier.fillMaxWidth().height(screenHeight * 0.92f - 150.dp)) {
                        UserList(data = dataList, vm)
                    }
                }
            }

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter)
            {
                Box(modifier = Modifier.fillMaxWidth().height(150.dp))
                {
                    Box(modifier = Modifier.fillMaxSize().padding(10.dp), contentAlignment = Alignment.TopStart)
                    {
                        Button(onClick = {
                            if (State.value == "Название") {
                                State.value = "Тип"
                            }
                            else if (State.value == "Тип") {
                                State.value = "Название"
                            }

                            if (message.value.text.isNotEmpty())
                            {
                                Search_Name = message.value.text
                                Search_Type = State.value

                            }
                            else
                            {
                                Search_Name = "None_Name"
                                Search_Type = "None_Type"
                            }
                        },
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = TabColor),
                            modifier = Modifier.width(140.dp).height(60.dp))
                        {
                            Text(text = State.value,
                                fontSize = 20.sp)
                        }
                    }

                    Box(modifier = Modifier.fillMaxSize().padding(10.dp), contentAlignment = Alignment.TopEnd)
                    {
                        Button(onClick = {scan()
                            Mainvm = vm},
                            shape = RoundedCornerShape(5.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = TabColor),
                            modifier = Modifier.width(60.dp).height(60.dp))
                        {

                        }
                        Image(
                            modifier = Modifier.padding(5.dp).width(50.dp).height(50.dp),
                            bitmap = ImageBitmap.imageResource(R.drawable.scan),
                            contentScale = ContentScale.Inside,
                            contentDescription = "-")
                    }

                    Box(modifier = Modifier.fillMaxSize().padding(10.dp), contentAlignment = Alignment.BottomCenter)
                    {

                        TextField(
                            modifier = Modifier.fillMaxWidth().height(60.dp),
                            shape = RoundedCornerShape(5.dp),
                            value = message.value,
                            onValueChange = {message.value = it
                                if (message.value.text.isNotEmpty())
                                {
                                    Search_Name = message.value.text
                                    Search_Type = State.value
                                }
                                else
                                {
                                    Search_Name = "None_Name"
                                    Search_Type = "None_Type"
                                }},
                            textStyle = TextStyle(fontSize =  24.sp),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color(0xffeeeeee),
                                unfocusedTextColor = Color(0xff888888),
                                focusedContainerColor = TabColor,
                                focusedTextColor = Color(0xff222222)
                            )
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun UserList(data:List<Data>, vm: DataViewModel = viewModel()) {
        DataList = data
        LazyColumn(Modifier.fillMaxWidth().fillMaxHeight()) {

            items(data) {u -> UserRow(u, vm)}
        }
    }

    fun DeleteData(name: String, quantity: Int)
    {
        if (quantity > 1)
        {
            Mainvm.updateQuantityMinus(name)
            Mainvm.updateWeightMinus(name)

            runOnUiThread{Toast.makeText(this@MainActivity, "Продукт изменён!", Toast.LENGTH_SHORT).show()}
        }
        else{
            Mainvm.deleteData(name)
        }

    }

    @Composable
    fun Search_Max_Kcal(cm: ChartDataViewModel = viewModel())
    {
        val chartdataList by cm.chartdataList.observeAsState(listOf())

        for (i in 0..chartdataList.size - 1)
        {
            if (chartdataList[i].kcal > Max_Chart_Kcal)
            {
                Max_Chart_Kcal = chartdataList[i].kcal
            }
        }
    }

    @Composable
    fun UserRow(data: Data, vm: DataViewModel = viewModel()) {
        var Show = false
        if (Search_Type == "None_Type")
        {
            Show = true
        }
        else
        {
            if (Search_Type == "Название")
            {
                if (Search_Name == "None_Type")
                {
                    Show = true
                }
                else if (Search_Name in data.name)
                {
                    Show = true
                }
            }
            else
            {
                if (Search_Name == "None_Type")
                {
                    Show = true
                }
                else if (Search_Name in data.type)
                {
                    Show = true
                }
            }
        }

        if (Show == true)
        {
            var isOpen by remember { mutableStateOf(false) }
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier.padding(start = 10.dp, top = 10.dp, end = 10.dp).fillMaxWidth().clickable { isOpen = !isOpen },
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                )
            ) {

                Box(modifier = Modifier.fillMaxHeight().padding(start = 5.dp))
                {
                    Column() {
                        Row() {
                            Box(modifier = Modifier.fillMaxHeight().padding(top = 10.dp, bottom = 10.dp, end = 10.dp))
                            {
                                Text(
                                    text = data.name,
                                    modifier = Modifier.padding(10.dp).background(Color.White),
                                    textAlign = TextAlign.Left,
                                    fontSize = 18.sp,
                                    fontWeight= FontWeight.Bold)
                            }

                            var Clicked by remember { mutableStateOf(0) }

                            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                                contentAlignment = Alignment.CenterEnd){
                                Button(onClick = {Clicked = 1},
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = DeleteColor),
                                    modifier = Modifier.padding(10.dp).width(40.dp).height(40.dp))
                                {

                                }

                                Box(modifier = Modifier.padding(10.dp).width(40.dp).height(40.dp), contentAlignment = Alignment.Center)
                                {
                                    Icon(
                                        Icons.Filled.Delete,
                                        contentDescription = "Удалить"
                                    )
                                }


                                if (Clicked == 1)
                                {
                                    Clicked = 0
                                    Mainvm = vm
                                    DeleteData(data.name, data.quantity)
                                    val CurrentCalendar = Calendar.getInstance()
                                    val CurrentDay = CurrentCalendar.get(Calendar.DAY_OF_MONTH)
                                    val CurrentMonth = CurrentCalendar.get(Calendar.MONTH)
                                    val CurrentYear = CurrentCalendar.get(Calendar.YEAR)
                                    Maincm.updateKcal(data.kcal, CurrentDay, CurrentMonth, CurrentYear)
                                    Search_Max_Kcal(Maincm)

                                }
                            }
                        }
                        if (isOpen)
                        {
                            Text(
                                text = data.type,
                                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp, end = 10.dp).background(Color.White),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp)
                            Text(
                                text = "Дата производства:",
                                modifier = Modifier.padding(start = 10.dp, bottom = 6.dp, end = 10.dp).background(Color.White),
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                color = Color.Gray)
                            Text(
                                text = data.date_of_manufacture,
                                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp, end = 10.dp).background(Color.White),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp)
                            Text(
                                text = "Дата истечения срока годности:",
                                modifier = Modifier.padding(start = 10.dp, bottom = 6.dp, end = 10.dp).background(Color.White),
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                color = Color.Gray)
                            Text(
                                text = data.expiration_date,
                                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp, end = 10.dp).background(Color.White),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp)
                            Text(
                                text = "Масса всего:",
                                modifier = Modifier.padding(start = 10.dp, bottom = 6.dp, end = 10.dp).background(Color.White),
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                color = Color.Gray)
                            Text(
                                text = data.all_weight.toString() + " кг",
                                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp, end = 10.dp).background(Color.White),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp)
                            Text(
                                text = "Масса одного:",
                                modifier = Modifier.padding(start = 10.dp, bottom = 6.dp, end = 10.dp).background(Color.White),
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                color = Color.Gray)
                            Text(
                                text = data.one_weight.toString() + " кг",
                                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp, end = 10.dp).background(Color.White),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp)
                            Text(
                                text = "Белки:",
                                modifier = Modifier.padding(start = 10.dp, bottom = 6.dp, end = 10.dp).background(Color.White),
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                color = Color.Gray)
                            Text(
                                text = data.proteins.toString(),
                                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp, end = 10.dp).background(Color.White),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp)
                            Text(
                                text = "Жиры:",
                                modifier = Modifier.padding(start = 10.dp, bottom = 6.dp, end = 10.dp).background(Color.White),
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                color = Color.Gray)
                            Text(
                                text = data.fats.toString(),
                                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp, end = 10.dp).background(Color.White),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp)
                            Text(
                                text = "Углеводы:",
                                modifier = Modifier.padding(start = 10.dp, bottom = 6.dp, end = 10.dp).background(Color.White),
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                color = Color.Gray)
                            Text(
                                text = data.carbohydrates.toString(),
                                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp, end = 10.dp).background(Color.White),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp)
                            Text(
                                text = "Ккал:",
                                modifier = Modifier.padding(start = 10.dp, bottom = 6.dp, end = 10.dp).background(Color.White),
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                color = Color.Gray)
                            Text(
                                text = data.kcal.toString(),
                                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp, end = 10.dp).background(Color.White),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp)
                            Text(
                                text = "Количество:",
                                modifier = Modifier.padding(start = 10.dp, bottom = 6.dp, end = 10.dp).background(Color.White),
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                color = Color.Gray)
                            Text(
                                text = data.quantity.toString() + " " + data.type_of_measurement,
                                modifier = Modifier.padding(start = 10.dp, bottom = 10.dp, end = 10.dp).background(Color.White),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun CheckChartData(cm: ChartDataViewModel = viewModel())
    {
        val chartdatalist by cm.chartdataList.observeAsState(listOf())

        ChartDataList = chartdatalist
        Maincm = cm

        var Values = listOf(-5, -4, -3, -2, -1, 0, 1)

        for (v in Values)
        {
            val CurrentCalendar = Calendar.getInstance()
            CurrentCalendar.add(Calendar.DAY_OF_MONTH, v + DateOffset)
            val CurrentDay = CurrentCalendar.get(Calendar.DAY_OF_MONTH)
            val CurrentMonth = CurrentCalendar.get(Calendar.MONTH)
            val CurrentYear = CurrentCalendar.get(Calendar.YEAR)

            var Create = true


            for (i in 0..ChartDataList.size - 1)
            {
                if (CurrentDay == ChartDataList[i].Day && CurrentMonth == ChartDataList[i].Month && CurrentYear == ChartDataList[i].Year)
                {
                    Create = false
                }
            }


            if (Create == true)
            {
                Maincm.changeDay(CurrentDay)
                Maincm.changeMonth(CurrentMonth)
                Maincm.changeYear(CurrentYear)
                Maincm.addChartData()
            }
        }
    }

    @Composable
    fun ChartMain(cm: ChartDataViewModel = viewModel())
    {
        val chartdataList by cm.chartdataList.observeAsState(listOf())

        ChartDataList = chartdataList
        Maincm = cm
        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.92f))
        {
            Column{
                Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.1f), contentAlignment = Alignment.Center)
                {
                    Text(text = "Аналитика потребления", fontSize = 30.sp, fontWeight= FontWeight.Bold)
                }

                Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.9f))
                {
                    ChartList(chartdataList, cm)
                }

                var Check by remember { mutableStateOf(0) }

                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter)
                {
                    ExtendedFloatingActionButton(
                        icon = {
                            Icon(
                                Icons.Filled.Refresh,
                                contentDescription = "Синхронизировать"
                            )
                        },
                        text = { Text("Синхронизировать", fontSize = 20.sp) },
                        shape = RoundedCornerShape(5.dp),
                        onClick = { Check = 1 },
                        modifier = Modifier.fillMaxWidth().padding(10.dp)
                    )
                }

                if (Check == 1)
                {
                    Check = 0
                    CheckChartData(cm)

                }
            }

        }
    }

    @Composable
    fun ChartList(chartdata: List<ChartData>, cm: ChartDataViewModel = viewModel()) {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
        {
            Column {
                val textMeasurer = rememberTextMeasurer()
                Canvas(modifier = Modifier.width(screenWidth).height(screenWidth + 25.dp)) {
                    val screenWidthToPx = configuration.screenWidthDp.dp.toPx()

                    val paddingInPx = 40.dp.toPx()

                    val xInPx = (screenWidthToPx - paddingInPx * 2) / 8
                    val yInPx = (screenWidthToPx - paddingInPx * 2) / 11

                    val RoundeMaxKcal = max(100, Max_Chart_Kcal + (100 - (Max_Chart_Kcal % 100)))

                    val One = RoundeMaxKcal / 10

                    val OneInPx = (screenWidthToPx - paddingInPx * 2) / 11

                    val PointsOX: MutableList<Offset> = mutableListOf()

                    var Calendarf = mutableStateOf(-1)


                    // OX

                    drawLine(
                        start = Offset(x = paddingInPx, y = screenWidthToPx - paddingInPx),
                        end = Offset(x = screenWidthToPx - paddingInPx, y = screenWidthToPx - paddingInPx),
                        color = Color.Blue,
                        strokeWidth = 12.0f,
                        cap = StrokeCap.Round
                    )
                    for (i in 1..7) {
                        drawLine(start = Offset(
                            x = paddingInPx + xInPx * i,
                            y = screenWidthToPx - paddingInPx - 5.dp.toPx()),
                            end = Offset(
                                x = paddingInPx + xInPx * i,
                                y = screenWidthToPx - paddingInPx + 5.dp.toPx()),
                            color = Color.Blue,
                            strokeWidth = 12.0f,
                            cap = StrokeCap.Round
                        )

                        val CurrentCalendar = Calendar.getInstance()
                        CurrentCalendar.add(Calendar.DAY_OF_MONTH, i - 6 - DateOffset)
                        val Day = CurrentCalendar.get(Calendar.DAY_OF_MONTH)
                        val Month = CurrentCalendar.get(Calendar.MONTH)
                        val Year = CurrentCalendar.get(Calendar.YEAR)

                        for (f in 0..chartdata.size - 1)
                        {
                            if (Day == chartdata[f].Day && Month == chartdata[f].Month && Year == chartdata[f].Year)
                            {
                                Calendarf.value = f
                                break
                            }
                        }

                        if (Calendarf.value != -1)
                        {
                            PointsOX.add(Offset(x = paddingInPx + xInPx * i, y = screenWidthToPx - paddingInPx - (ChartDataList[Calendarf.value].kcal / One * OneInPx)))
                        }

                        var day_string = Day.toString()
                        if (Day < 10) {day_string = "0" + day_string}
                        var month_string = (Month + 1).toString()
                        if (Month < 10) {month_string = "0" + month_string}
                        drawText(textMeasurer, text = day_string.toString() + "." + month_string.toString(), topLeft = Offset(x = paddingInPx + xInPx * i - 15.dp.toPx(), y = screenWidthToPx - paddingInPx + 10.dp.toPx()))
                        drawText(textMeasurer, text = Year.toString(), topLeft = Offset(x = paddingInPx + xInPx * i - 15.dp.toPx(), y = screenWidthToPx - paddingInPx + 25.dp.toPx()))
                    }

                    // OY

                    drawLine(
                        start = Offset(x = paddingInPx, y = paddingInPx),
                        end = Offset(x = paddingInPx, y = screenWidthToPx - paddingInPx),
                        color = Color.Blue,
                        strokeWidth = 12.0f,
                        cap = StrokeCap.Round
                    )

                    for (i in 0..10) {
                        drawLine(
                            start = Offset(
                                x = paddingInPx - 5.dp.toPx(),
                                y = screenWidthToPx - paddingInPx - yInPx * i
                            ),
                            end = Offset(
                                x = paddingInPx + 5.dp.toPx(),
                                y = screenWidthToPx - paddingInPx - yInPx * i
                            ),
                            color = Color.Blue,
                            strokeWidth = 12.0f,
                            cap = StrokeCap.Round
                        )

                        drawText(textMeasurer, text = (One * i).toString(), topLeft = Offset(x = 2.dp.toPx(), y = screenWidthToPx - paddingInPx - yInPx * i - 7.dp.toPx()))
                    }

                    for (g in 0..PointsOX.size - 2)
                    {
                        drawLine(start = PointsOX[g],
                            end = PointsOX[g + 1],
                            color = Color.Red,
                            strokeWidth = 12.0f,
                            cap = StrokeCap.Round)
                    }
                }

                Row{
                    var ButtonState = remember { mutableStateOf("") }
                    Box(modifier = Modifier.fillMaxWidth(0.5f).fillMaxSize(0.3f))
                    {
                        ExtendedFloatingActionButton(
                            icon = { Icon(Icons.Filled.ArrowBack, contentDescription = "Влево") },
                            text = { Text(text = "Влево", fontSize = 20.sp) },
                            shape = RoundedCornerShape(5.dp),
                            onClick = {
                                ButtonState.value = "<-"
                            },
                            modifier = Modifier.fillMaxSize().padding(start = 10.dp, top = 10.dp, bottom = 10.dp, end = 5.dp)
                        )
                    }

                    Box(modifier = Modifier.fillMaxWidth().fillMaxSize(0.3f))
                    {
                        ExtendedFloatingActionButton(
                            icon = { Icon(Icons.Filled.ArrowForward, contentDescription = "Вправо") },
                            text = { Text(text = "Вправо", fontSize = 20.sp) },
                            shape = RoundedCornerShape(5.dp),
                            onClick = {
                                ButtonState.value = "->"
                            },
                            modifier = Modifier.fillMaxSize().padding(start = 5.dp, top = 10.dp, bottom = 10.dp, end = 10.dp)
                        )
                    }

                    if (ButtonState.value == "->")
                    {
                        DateOffset -= 1
                        CheckChartData(cm)
                        ButtonState.value = ""
                    }
                    else if (ButtonState.value == "<-")
                    {
                        DateOffset += 1
                        CheckChartData(cm)
                        ButtonState.value = ""
                    }
                }
            }
        }
    }

    @Composable
    fun ChartRow(chartdata: ChartData, cm: ChartDataViewModel = viewModel()) {
        Text(text = chartdata.Day.toString() + " " + chartdata.Month.toString() + " " + chartdata.Year.toString())
    }

    @ExperimentalPagerApi
    @Composable
    fun TabLayout()
    {
        val pagerState = rememberPagerState(pageCount = 3)
        Column(modifier = Modifier.background(Color.White))
        {
            TabsContent(pagerState = pagerState)
            Tabs(pagerState = pagerState)
        }
    }

    @ExperimentalPagerApi
    @Composable
    fun Tabs(pagerState: PagerState) {
        val list = listOf(
            R.drawable.home,
            R.drawable.shopping_list,
            R.drawable.analytics
        )
        val scope = rememberCoroutineScope()
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = TabColor,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                    height = 2.dp,
                    color = Color.White
                )
            }
        ) {
            list.forEachIndexed { index, _ ->
                Tab(
                    icon = {
                        Image(
                            bitmap = ImageBitmap.imageResource(list[index]),
                            contentDescription = "-",
                        )
                    },
                    selected = pagerState.currentPage == index,
                    modifier = Modifier.fillMaxHeight(),
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
    }

    @ExperimentalPagerApi
    @Composable
    fun TabsContent(pagerState: PagerState)
    {
        HorizontalPager(state = pagerState) {
                page ->
            when (page) {
                0 -> TabContentScreen(data = "home")
                1 -> TabContentScreen(data = "shopping list")
                2 -> TabContentScreen(data = "an")
            }
        }
    }

    @Composable
    fun TabContentScreen(data: String)
    {
        if (data == "home")
        {
            val owner = LocalViewModelStoreOwner.current

            owner?.let {
                val viewModel: ChartDataViewModel = viewModel(
                    it,
                    "ChartDataViewModel",
                    ChartDataViewModelFactory(LocalContext.current.applicationContext as Application)
                )
                Maincm = viewModel
                val chartdataList by Maincm.chartdataList.observeAsState(listOf())
                ChartDataList = chartdataList

            }

            owner?.let {
                val viewModel: DataViewModel = viewModel(
                    it,
                    "DataViewModel",
                    DataViewModelFactory(LocalContext.current.applicationContext as Application)
                )
                Main(viewModel)
            }

        }
        else if (data == "shopping list")
        {
            val owner = LocalViewModelStoreOwner.current

            owner?.let {
                val viewModel: ShopDataViewModel = viewModel(
                    it,
                    "ShopDataViewModel",
                    ShopDataViewModelFactory(LocalContext.current.applicationContext as Application)
                )
                ShopMain(viewModel)
            }
        }
        else if (data == "an"){
            val owner = LocalViewModelStoreOwner.current

            owner?.let {
                val viewModel: ChartDataViewModel = viewModel(
                    it,
                    "ChartDataViewModel",
                    ChartDataViewModelFactory(LocalContext.current.applicationContext as Application)
                )
                Search_Max_Kcal(viewModel)
                ChartMain(viewModel)
            }

        }

    }
}