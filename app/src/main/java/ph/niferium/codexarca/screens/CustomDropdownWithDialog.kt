package ph.niferium.codexarca.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

//@Composable
//fun CustomDropdownWithDialog(
//    label: String = "Select an option",
//    items: List<String>,
//    selectedItem: String?,
//    onItemSelected: (String) -> Unit
//) {
//
//    Column {
//        OutlinedTextField(
//            value = selectedItem ?: "",
//            onValueChange = {},
//            readOnly = true,
//            label = { Text(label) },
//            trailingIcon = {
//                Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable { onItemSelected() }
//        )
//    }
//}