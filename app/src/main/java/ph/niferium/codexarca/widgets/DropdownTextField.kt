package ph.niferium.codexarca.widgets

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay

@Composable
fun DropdownTextField(
    @StringRes label: Int,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "Select Option",
    options: List<String> = emptyList(),
    modifier: Modifier = Modifier,
    onDropdownClick: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    val buttonClickStates = remember { mutableStateMapOf<Int, Boolean>() }
    val colorChangeStates = remember { mutableStateMapOf<Int, Color>() }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = stringResource(id = label),
            style = TextStyle(
                fontSize = 14.sp
            ),
        )

        OutlinedTextField(
            value = value,
            onValueChange = { }, // Read-only
            label = { Text(hint) },
            readOnly = true,
            trailingIcon = {
                IconButton(
                    onClick = {
                        expanded = !expanded
                        if (expanded) {
                            onDropdownClick() // Fetch data when dropdown opens
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Dropdown Arrow"
                    )
                }
            },
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Gray,
                focusedBorderColor = Color.Black,
                unfocusedContainerColor = Color.Gray,
                focusedContainerColor = Color.Gray,
                errorContainerColor = Color.Gray,
                errorBorderColor = Color.Red
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable {
                    expanded = !expanded
                    if (expanded) {
                        onDropdownClick() // Fetch data when field is clicked
                    }
                }
        )
        if(expanded) {
            Dialog(
                onDismissRequest = { expanded = false  }
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(508.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column {
                        Box(
                            Modifier.fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(top = 16.dp, start = 16.dp)
                                    .align(Alignment.TopStart),
                                text ="Hello World",
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    lineHeight = 24.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold)
                            )
                            Text(
                                modifier = Modifier
                                    .padding(top = 16.dp, end = 16.dp)
                                    .align(Alignment.TopEnd),
                                text ="Close",
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    lineHeight = 24.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold)
                            )
                        }
                        LazyColumn(
                            modifier = Modifier
                                .padding(top = 32.dp)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            items(options.size) { index ->
                                val isButtonClick = buttonClickStates[index] ?: false
                                val colorClick = colorChangeStates[index] ?: Color.Transparent

                                Column(
                                    Modifier
                                        .background(colorClick)
                                        .clickable {
                                            buttonClickStates[index] = !isButtonClick
                                            colorChangeStates[index] = Color.Cyan
                                        }
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .padding(16.dp),
                                        text = ":Item ${options[index]}",
                                        style = TextStyle(
                                            fontSize = 18.sp,
                                            lineHeight = 24.sp,
                                            color = Color.Black)
                                    )
                                    Spacer(modifier = Modifier
                                        .height(1.dp)
                                        .fillMaxWidth()
                                        .background(Color.Black)
                                    )
                                }

                                if (isButtonClick) {
                                    LaunchedEffect(key1 = index) {
                                        delay(100L)
                                        colorChangeStates[index] = Color.Transparent
                                        buttonClickStates[index] = false
                                        expanded = false
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            if (options.isEmpty()) {
//                DropdownMenuItem(
//                    text = { Text("Loading...") },
//                    onClick = { }
//                )
//            } else {
//                options.forEach { option ->
//                    DropdownMenuItem(
//                        text = { Text(option) },
//                        onClick = {
//                            onValueChange(option)
//                            expanded = false
//                        }
//                    )
//                }
//            }
//        }
    }
}