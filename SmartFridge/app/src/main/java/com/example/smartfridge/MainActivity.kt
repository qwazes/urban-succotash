package com.example.smartfridge

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartfridge.data.Dao
import com.example.smartfridge.data.MainDb
import com.example.smartfridge.data.Product
import com.example.smartfridge.ui.theme.SmartFridgeTheme
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var mainDb: MainDb
    var counter = 0

    private val scanLauncher = registerForActivityResult(ScanContract())
    { result ->
        if (result.contents == null) {
            Toast.makeText(this, "Не удалось отсканировать продукт", Toast.LENGTH_SHORT).show()
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                val productByQr = mainDb.dao.getProductsByQr(result.contents)

                if (productByQr == null)
                {
                    mainDb.dao.insertProduct(Product(null, "Product ${counter++}", result.contents))
                    runOnUiThread{Toast.makeText(this@MainActivity, "Продукт сохранён!", Toast.LENGTH_SHORT).show()}
                }
                else
                {
                    runOnUiThread{Toast.makeText(this@MainActivity, "Продукт уже сохранён!", Toast.LENGTH_SHORT).show()}
                }
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            SmartFridgeTheme {
                Column {
                    Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.93f).background(Color.Red))
                    {
                        NavHost(navController, startDestination = NavRoutes.Home.route)
                        {
                            composable(NavRoutes.Home.route) { Home(mainDb) }
                            composable(NavRoutes.Analytics.route) { Analytics() }
                            composable(NavRoutes.Shopping_List.route) { Shopping_List() }
                            composable(NavRoutes.Settings.route) { Settings() }
                        }
                    }

                    Navigatio_Bar(navController)
                }

            }
        }
    }
    private fun scan() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.DATA_MATRIX)
        options.setPrompt("Scan")
        options.setCameraId(0) // Use a specific camera of the device
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        scanLauncher.launch(options)
    }

    @Composable
    fun Navigatio_Bar(navController: NavController)
    {
        val configuration = LocalConfiguration.current
        val Width = configuration.screenWidthDp.dp / 5

        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight().background(Color.White))
        {
            Row()
            {
                val Button_Modifier = Modifier.fillMaxHeight().width(Width)

                Image(bitmap = ImageBitmap.imageResource(R.drawable.home),
                    contentDescription = "Home",
                    contentScale = ContentScale.Inside,
                    modifier = Button_Modifier.then(Modifier.fillMaxWidth().background(Color.White).clickable { navController.navigate(NavRoutes.Home.route) }))

                Image(bitmap = ImageBitmap.imageResource(R.drawable.scan),
                    contentDescription = "Scan",
                    contentScale = ContentScale.Inside,
                    modifier = Button_Modifier.then(Modifier.fillMaxWidth().background(Color.White).clickable { scan() }))

                Image(bitmap = ImageBitmap.imageResource(R.drawable.analytics),
                    contentDescription = "Analytics",
                    contentScale = ContentScale.Inside,
                    modifier = Button_Modifier.then(Modifier.fillMaxWidth().background(Color.White).clickable { navController.navigate(NavRoutes.Analytics.route) }))

                Image(bitmap = ImageBitmap.imageResource(R.drawable.shopping_list),
                    contentDescription = "Shopping_Lists",
                    contentScale = ContentScale.Inside,
                    modifier = Button_Modifier.then(Modifier.fillMaxWidth().background(Color.White).clickable { navController.navigate(NavRoutes.Shopping_List.route) }))

                Image(bitmap = ImageBitmap.imageResource(R.drawable.settings),
                    contentDescription = "Settings",
                    contentScale = ContentScale.Inside,
                    modifier = Button_Modifier.then(Modifier.fillMaxWidth().background(Color.White).clickable { navController.navigate(NavRoutes.Settings.route) }))
            }
        }
    }
}

@Composable
fun Home(mainDb: MainDb)
{
    val productStateList = mainDb.dao.getAllProducts().collectAsState(initial = emptyList())
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally)
    {
        LazyColumn(modifier = Modifier.padding(top = 15.dp).background(Color.Red).fillMaxWidth().fillMaxHeight(0.92f))
        {
            items(productStateList.value)
            {
                    product ->
                Spacer(modifier = Modifier.height(10.dp))
                Card(modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.elevatedCardElevation(5.dp))
                {
                    Box(modifier = Modifier.fillMaxWidth().background(Color.White).padding(10.dp))
                    {
                        Text(text = "${product.numberQR}", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, color = Color.Black)
                    }
                }
            }
        }
    }
}

@Composable
fun Analytics()
{
    Text("Analytics Page", fontSize = 30.sp)
}

@Composable
fun Shopping_List()
{
    Text("Shopping List Page", fontSize = 30.sp)
}

@Composable
fun Settings()
{
    Text("Settings Page", fontSize = 30.sp)
}

sealed class NavRoutes(val route: String)
{
    object Home : NavRoutes("home")
    object Analytics : NavRoutes("analytics")
    object Shopping_List : NavRoutes("shopping list")
    object Settings : NavRoutes("settings")
}