package xstitchcatwalk.canvassize

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
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
import kotlinx.coroutines.launch
import xstitchcatwalk.canvassize.viewmodel.StitchersAppViewModel
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
    val viewModel: StitchersAppViewModel = hiltViewModel()
    val iconTint = MaterialTheme.colorScheme.onSurface

    val menuBackgroundColor = MaterialTheme.colorScheme.surfaceVariant
    val menuHeaderColor = MaterialTheme.colorScheme.primary
/*    val activeItemColor = MaterialTheme.colorScheme.primaryContainer
    val inactiveItemColor = MaterialTheme.colorScheme.surfaceVariant
    val onActiveItemColor = MaterialTheme.colorScheme.onPrimaryContainer
    val onInactiveItemColor = MaterialTheme.colorScheme.onSurfaceVariant*/

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight()
                    .background(menuBackgroundColor),
                tonalElevation = 8.dp,
                shape = MaterialTheme.shapes.extraLarge.copy(
                    topEnd = CornerSize(0.dp),
                    bottomEnd = CornerSize(16.dp)
                )
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(menuBackgroundColor)
                        .width(IntrinsicSize.Max)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        menuHeaderColor,
                                        menuHeaderColor.copy(alpha = 0.85f)
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
                        label = { Text(stringResource(R.string.canvas_size_calculator)) },
                        selected = selectedItem == 0,
                        onClick = {
                            selectedItem = 0
                            scope.launch { drawerState.close() }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.outline_canvas_24),
                                contentDescription = stringResource(R.string.canvas_size_calculator),
                                tint = iconTint
                            )
                        }
                    )

                    NavigationDrawerItem(
                        label = { Text(stringResource(R.string.threads_consumption_calculator)) },
                        selected = selectedItem == 1,
                        onClick = {
                            selectedItem = 1
                            scope.launch { drawerState.close() }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.outline_palette_24),
                                contentDescription = stringResource(R.string.threads_consumption_calculator),
                                tint = iconTint
                            )
                        }
                    )

                    NavigationDrawerItem(
                        label = { Text(stringResource(R.string.stitching_time_timer)) },
                        selected = selectedItem == 2,
                        onClick = {
                            selectedItem = 2
                            scope.launch { drawerState.close() }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.outline_timer_24),
                                contentDescription = stringResource(R.string.stitching_time_timer),
                                tint = iconTint
                            )
                        }
                    )

                    NavigationDrawerItem(
                        label = { Text(stringResource(R.string.settings_in_app)) },
                        selected = selectedItem == 3,
                        onClick = {
                            selectedItem = 3
                            scope.launch { drawerState.close() }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.outline_settings_24),
                                contentDescription = stringResource(R.string.settings_in_app),
                                tint = iconTint
                            )
                        }
                    )

                }
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
    val viewModel: StitchersAppViewModel = hiltViewModel()

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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThreadsConsumptionCalculateScreen() {
    val viewModel: StitchersAppViewModel = hiltViewModel()

    val techniques = listOf(
        R.string.cross_stitch_technique,
        R.string.halfcross_technique,
        R.string.backstitch_technique
    ).map { stringResource(it) }

    val stitches by viewModel.stitches.collectAsStateWithLifecycle()
    val fabricCount by viewModel.fabricCount.collectAsStateWithLifecycle()
    val strands by viewModel.strands.collectAsStateWithLifecycle()
    var expanded by remember {mutableStateOf(false)}

    val result by viewModel.threadUsageResult.collectAsStateWithLifecycle()

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(Modifier
        .fillMaxWidth()
        .padding(top = 64.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = stitches,
            onValueChange = { newValue ->
                viewModel.updateStitches(newValue) },
            label = {Text(
                stringResource(R.string.set_stitches_number),
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
                .width(300.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp)
        )

        OutlinedTextField(
            value = fabricCount,
            onValueChange = { newValue ->
                viewModel.updateFabricCount(newValue)
            },
            label = {Text(
                stringResource(R.string.set_fabric_count),
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
                .width(300.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp)
        )

        OutlinedTextField(
            value = strands,
            onValueChange = { newValue ->
                viewModel.updateStrands(newValue) },
            label = {Text(
                stringResource(R.string.set_strands),
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
                .width(300.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp)
        )


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .width(300.dp),
                expanded = expanded,
                onExpandedChange = {expanded = it}
            ) {
                OutlinedTextField(
                    value = techniques.firstOrNull {
                        it == stringResource(viewModel.technique.value)
                    } ?: "",
                    onValueChange = {},
                    label = {
                        Text(
                            stringResource(R.string.set_stitch_technique),
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            style = MaterialTheme.typography.labelLarge
                        )
                    },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )},
                    modifier = Modifier
                        .width(300.dp)
                        .padding(top = 8.dp)
                        .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {expanded = false}
                ) {
                    techniques.forEachIndexed { index, technique ->
                        DropdownMenuItem(
                            text = {Text(
                                technique,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                style = MaterialTheme.typography.labelLarge
                            )},
                            onClick = {
                                viewModel.updateTechnique(
                                    when(index) {
                                        0 -> R.string.cross_stitch_technique
                                        1 -> R.string.halfcross_technique
                                        2 -> R.string.backstitch_technique
                                        else -> R.string.cross_stitch_technique
                                    }
                                )
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        ElevatedButton(
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally)
                .wrapContentWidth(),
            onClick = {
                keyboardController?.hide()
                viewModel.calculateThreadUsage()
            },
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            )
        ) {
            Text(
                text = stringResource(R.string.buttonTextCalculateConsumption),
                style = MaterialTheme.typography.labelLarge
            )
        }

        result?.let { usage ->
            Text(
                text = stringResource(R.string.thread_usage_result, usage),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun TimerScreen() {
    val context = LocalContext.current
    val viewModel: StitchersAppViewModel = hiltViewModel()
    val isRunning by viewModel.TimerIsRunning.collectAsStateWithLifecycle()
    val elapsedTime by viewModel.elapsedTime.collectAsStateWithLifecycle()
    val notification by viewModel.showNotification.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = { viewModel.toggleTimer() },
                modifier = Modifier.size(120.dp)
            ) {
                Icon(
                    painter = painterResource(
                        if (isRunning) R.drawable.baseline_pause_circle_outline_24 else R.drawable.baseline_play_circle_outline_24
                    ),
                    contentDescription = if (isRunning) "Pause" else "Play",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(80.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Непосредственно таймер
            Text(
                text = formatTime(elapsedTime),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Кнопка сброса под временем
            if (elapsedTime > 0) {
                TextButton(
                    onClick = { viewModel.resetTimer() }
                ) {
                    Text(
                        stringResource(R.string.reset_timer_text),
                        style = MaterialTheme.typography.labelLarge)
                }
            }
        }

        // Показ уведомлений при достижении определенного времени вышивания
        notification?.let { (show, message) ->
            if (show) {
                ShowNotification(
                    message = message,
                    onDismiss = { viewModel.dismissNotification() }
                )
            }
        }
    }
}

@Composable
fun ShowNotification(message: String, onDismiss: () -> Unit) {
    val randomX = remember { Random.nextInt(-150, 150) }
    val randomY = remember { Random.nextInt(-100, 100) }
    val bubbleShape = MaterialTheme.shapes.extraLarge.copy(
        topStart = CornerSize(0.dp),
        bottomEnd = CornerSize(0.dp)
    )

    Box(
        modifier = Modifier
            .offset(x = randomX.dp, y = randomY.dp)
            .clip(bubbleShape)
            .background(MaterialTheme.colorScheme.surface)
            .shadow(4.dp, bubbleShape)
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "💡",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onDismiss,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                shape = MaterialTheme.shapes.small
            ) {
                Text(stringResource(R.string.OK))
            }
        }
    }
}

fun formatTime(seconds: Long): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    return String.format("%02d:%02d", hours, minutes)
}

@Composable
fun SettingsScreen() {
    Text("Settings screen")
}



@RequiresApi(Build.VERSION_CODES.Q)
@Preview(showBackground = true)
@Composable
fun CalculateCanvasSizePreview() {
    AppTheme {
        CanvasSizeCalculatorScreen()
    }
}