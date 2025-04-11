package xstitchcatwalk.canvassize

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import xstitchcatwalk.canvassize.preview.FakeCanvasSizeViewModel
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.launch
import xstitchcatwalk.canvassize.viewmodel.StitchersAppViewModel

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
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CrossStitchersApp(modifier: Modifier = Modifier) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Text("Меню", modifier = Modifier.padding(16.dp))
            // Пункты меню...
        },
        modifier = TODO(),
        gesturesEnabled = TODO(),
        scrimColor = TODO(),
        content = TODO()
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Вышивка крестиком") },
                navigationIcon = {
                    IconButton(
                        onClick = { scope.launch { drawerState.open() } }
                    ) {
                        Icon(Icons.Default.Menu, null)
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "calculator",
            modifier = Modifier.padding(padding)
        ) {
            composable("calculator") { CanvasSizeCalculatorScreen(viewModel = StitchersAppViewModel()) }
                //composable("threads") { ThreadCalculatorScreen() }
            //composable("timer") { StitchingTimerScreen() }
            //composable("settings") { SettingsScreen() }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CanvasSizeCalculatorScreen(
    modifier: Modifier = Modifier,
    viewModel: FakeCanvasSizeViewModel
) {
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
                            text = "Аида $count: ${"%.2f".format(size.first)} см × ${"%.2f".format(size.second)} см",
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

@RequiresApi(Build.VERSION_CODES.Q)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        CanvasSizeCalculatorScreen(viewModel = FakeCanvasSizeViewModel())
    }
}