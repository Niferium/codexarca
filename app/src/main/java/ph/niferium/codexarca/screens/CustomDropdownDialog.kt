package ph.niferium.codexarca.screens

//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.heightIn
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.layout.wrapContentHeight
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowDropDown
//import androidx.compose.material.icons.filled.Close
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Divider
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.RadioButton
//import androidx.compose.material3.RadioButtonDefaults
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.window.Dialog
//import androidx.compose.ui.window.DialogProperties
//
//@Composable
//fun CustomDropdownDialog(
//    items: List<String>,
//    selectedItem: String?,
//    onItemSelected: (String) -> Unit,
//    label: String = "Select an item",
//    dialogTitle: String = "Choose Option",
//    modifier: Modifier = Modifier
//) {
//    var showDialog by remember { mutableStateOf(false) }
//
//    // Dropdown trigger
//    Card(
//        modifier = modifier
//            .fillMaxWidth()
//            .clickable { showDialog = true },
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
//        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Column(modifier = Modifier.weight(1f)) {
//                Text(
//                    text = label,
//                    style = MaterialTheme.typography.labelMedium,
//                    color = MaterialTheme.colorScheme.onSurfaceVariant
//                )
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                    text = selectedItem ?: "Select an option",
//                    style = MaterialTheme.typography.bodyLarge,
//                    color = if (selectedItem != null)
//                        MaterialTheme.colorScheme.onSurface
//                    else
//                        MaterialTheme.colorScheme.onSurfaceVariant
//                )
//            }
//            Icon(
//                imageVector = Icons.Default.ArrowDropDown,
//                contentDescription = "Dropdown arrow",
//                tint = MaterialTheme.colorScheme.onSurfaceVariant
//            )
//        }
//    }
//
//    // Dialog
//    if (showDialog) {
//        Dialog(
//            onDismissRequest = { showDialog = false },
//            properties = DialogProperties(usePlatformDefaultWidth = false)
//        ) {
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth(0.9f)
//                    .wrapContentHeight(),
//                shape = RoundedCornerShape(16.dp),
//                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
//            ) {
//                Column(
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    // Dialog header with title and close button
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(
//                            text = dialogTitle,
//                            style = MaterialTheme.typography.headlineSmall,
//                            color = MaterialTheme.colorScheme.onSurface
//                        )
//                        IconButton(
//                            onClick = { showDialog = false }
//                        ) {
//                            Icon(
//                                imageVector = Icons.Default.Close,
//                                contentDescription = "Close dialog",
//                                tint = MaterialTheme.colorScheme.onSurfaceVariant
//                            )
//                        }
//                    }
//
//                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f))
//
//                    // Items list
//                    LazyColumn(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .heightIn(max = 400.dp)
//                    ) {
//                        items(items.size) { item ->
//                            Row(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .clickable {
//                                        onItemSelected(item)
//                                        showDialog = false
//                                    }
//                                    .padding(horizontal = 16.dp, vertical = 12.dp),
//                                verticalAlignment = Alignment.CenterVertically
//                            ) {
//                                RadioButton(
//                                    selected = selectedItem == item,
//                                    onClick = {
//                                        onItemSelected(item)
//                                        showDialog = false
//                                    },
//                                    colors = RadioButtonDefaults.colors(
//                                        selectedColor = MaterialTheme.colorScheme.primary
//                                    )
//                                )
//                                Spacer(modifier = Modifier.width(12.dp))
//                                Text(
//                                    text = item,
//                                    style = MaterialTheme.typography.bodyLarge,
//                                    color = MaterialTheme.colorScheme.onSurface
//                                )
//                            }
//                        }
//                    }
//
//                    Spacer(modifier = Modifier.height(8.dp))
//                }
//            }
//        }
//    }
//}