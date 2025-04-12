package xstitchcatwalk.canvassize

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xstitchcatwalk.canvassize.ui.theme.AppTheme
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import xstitchcatwalk.canvassize.viewmodel.StitchersAppViewModel
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.background
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

class MainActivity : ComponentActivity() {
    private val viewModel: StitchersAppViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CrossStitchersApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrossStitchersApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf(0) }
    val viewModel: StitchersAppViewModel = viewModel()

    val iconTint = MaterialTheme.colorScheme.onSurface


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.primaryContainer
                                )
                            )
                        )
                ) {
                    Text(
                        stringResource(R.string.menu),
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                NavigationDrawerItem(
                    label = { Text("Калькулятор размера канвы") },
                    selected = selectedItem == 0,
                    onClick = {
                        selectedItem = 0
                        scope.launch { drawerState.close() }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.outline_canvas_24),
                            contentDescription = "Калькулятор размера канвы",
                            tint = iconTint
                        )
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Расчет расхода нитей") },
                    selected = selectedItem == 1,
                    onClick = {
                        selectedItem = 1
                        scope.launch { drawerState.close() }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.outline_palette_24),
                            contentDescription = "Расчет расхода нитей",
                            tint = iconTint
                        )
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Таймер вышивания") },
                    selected = selectedItem == 2,
                    onClick = {
                        selectedItem = 2
                        scope.launch { drawerState.close() }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.outline_timer_24),
                            contentDescription = "Таймер вышивания",
                            tint = iconTint
                        )
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Настройки") },
                    selected = selectedItem == 3,
                    onClick = {
                        selectedItem = 3
                        scope.launch { drawerState.close() }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.outline_settings_24),
                            contentDescription = "Настройки",
                            tint = iconTint
                        )
                    }
                )

            }
        }
    ) {

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.outline_menu_24),
                                contentDescription = stringResource(R.string.menu),
                                tint = iconTint
                            )
                        }
                    }
                )
            },

            content = { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    when (selectedItem) {
                        0 -> CanvasSizeCalculatorScreen()
                        1 -> ThreadsConsumptionCalculateScreen()
                        2 -> TimerScreen()
                        3 -> SettingsScreen()
                    }
                }
            }
        )
    }
}


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CanvasSizeCalculatorScreen(
    modifier: Modifier.Companion = Modifier
) {
    val viewModel: StitchersAppViewModel = viewModel()

    Column(Modifier
        .fillMaxWidth()
        .padding(top = 64.dp),
        verticalArrangement = Arrangement.SpaceBetween) {

        val embroideryWidth by viewModel.widthInStitches.collectAsStateWithLifecycle()
        val embroideryHeight by viewModel.heightInStitches.collectAsStateWithLifecycle()
        val resCanvasSize by viewModel.result.collectAsState()

        val keyboardController = LocalSoftwareKeyboardController.current

        Text(
            text = stringResource(R.string.embroidery_size_title),
            style = MaterialTheme.typography.titleLarge.copy(
                textAlign = TextAlign.Center
            ),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )

        OutlinedTextField(
            value = embroideryWidth,
            onValueChange = { newValue ->
                viewModel.updateWidth(newValue)},
            label = {Text(
                text = stringResource(R.string.width_title),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.labelLarge
            )},
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.outline,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .width(120.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp)
        )

        OutlinedTextField(
            value = embroideryHeight,
            onValueChange = { newValue ->
                viewModel.updateHeight(newValue)},
            label = {Text(
                text = stringResource(R.string.heigth_title),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.labelLarge
            )},
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.outline,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .width(120.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp)
        )

        ElevatedButton(
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally)
                .wrapContentWidth(),
            onClick = {
                keyboardController?.hide()
                viewModel.calculateCanvasSize()
            },
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            )
        ) {
            Text(
                text = stringResource(R.string.buttonTextCalculateSize),
                style = MaterialTheme.typography.labelLarge
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        resCanvasSize?.let { canvasSizes ->
            Column(modifier = Modifier.padding(top = 16.dp)) {
                Text(
                    text = stringResource(R.string.embroidery_sizes_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                canvasSizes.forEach { (count, size) ->
                    Card(modifier = Modifier
                        .padding(vertical = 8.dp)
                        .align(Alignment.CenterHorizontally),
                            shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )) {
                        Text(
                            text = stringResource(
                                R.string.aida_size_template,
                                count,
                                size.first,
                                size.second
                            ),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ThreadsConsumptionCalculateScreen() {
    Text("Расчет расхода нитей")
}

@Composable
fun TimerScreen() {
    Text("Timer screen")
}

@Composable
fun SettingsScreen() {
    Text("Settings screen")
}

@RequiresApi(Build.VERSION_CODES.Q)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        CanvasSizeCalculatorScreen()
    }
}