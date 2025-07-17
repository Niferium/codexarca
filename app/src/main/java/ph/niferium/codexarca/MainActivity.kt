package ph.niferium.codexarca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ph.niferium.codexarca.screens.ImagePickerScreen
import ph.niferium.codexarca.screens.ImagePickerScreenCropping
import ph.niferium.codexarca.ui.theme.CodexarcaTheme
import ph.niferium.codexarca.widgets.DropdownTextField

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var selected by remember { mutableStateOf<String?>(null) }
            var dialogOpen by remember { mutableStateOf(false) }
            CodexarcaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Greeting(
                            name = "Niforos",
                            modifier = Modifier.padding(innerPadding)
                        )


//                        CustomDropdownWithDialog(
//                            label = "Select Item",
//                            items = listOf("Apple", "Banana", "Cherry", "Durian"),
//                            selectedItem = selected,
//                            onItemSelected = { selected = it }
//                        )
                        DropdownTextField(
                            label = R.string.codex_arca,
                            value = "",
                            hint = "",
                            options = listOf("Apple", "Banana", "Cherry", "Durian"),
                            onValueChange = {

                            }
                        )
                        //Text("Android 安卓 and Huawei 华为")
                        //ImagePickerScreen()
                        //ImagePickerScreenCropping()
                    }

//                    if (dialogOpen) {
//                        Dialog(onDismissRequest = { dialogOpen = false }) {
//                            Surface(
//                                shape = RoundedCornerShape(16.dp),
//                                tonalElevation = 8.dp,
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(16.dp)
//                            ) {
//                                Column(
//                                    modifier = Modifier
//                                        .padding(16.dp)
//                                        .verticalScroll(rememberScrollState())
//                                ) {
//                                    // Dialog Header
//                                    Row(
//                                        modifier = Modifier.fillMaxWidth(),
//                                        verticalAlignment = Alignment.CenterVertically
//                                    ) {
//                                        Text(
//                                            text = "Choose an Option",
//                                            style = MaterialTheme.typography.titleMedium,
//                                            modifier = Modifier.weight(1f)
//                                        )
//                                        IconButton(onClick = { dialogOpen = false }) {
//                                            Icon(Icons.Default.Close, contentDescription = "Close")
//                                        }
//                                    }
//
//                                    Spacer(modifier = Modifier.height(8.dp))
//
//                                    HorizontalDivider()
//
//                                    // Items
//                                    items.forEach { item ->
//                                        Column(
//                                            modifier = Modifier
//                                                .fillMaxWidth()
//                                                .clickable {
//                                                    onItemSelected(item)
//                                                    dialogOpen = false
//                                                }
//                                                .padding(vertical = 12.dp)
//                                        ) {
//                                            Text(
//                                                text = item,
//                                                style = MaterialTheme.typography.bodyLarge,
//                                                modifier = Modifier.padding(horizontal = 8.dp)
//                                            )
//                                            Divider(modifier = Modifier.padding(top = 12.dp))
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CodexarcaTheme {
        Greeting("Android")
    }
}