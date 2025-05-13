package ph.niferium.codexarca.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ph.niferium.codexarca.R


//Basic Login Screen
@Composable
fun LoginScreen() {

    val focusManager = LocalFocusManager.current
    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    val isValidating by remember { mutableStateOf(false) }

    //Draw the box
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        //The Insides of the box
        //Commonly used inside are Columns, Rows, Scaffolds(For with Headers)
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            //Set weight to 1f if you wanted a separation between bottom and the top content
            //Best Practice Tip: put hardcoded strings to the strings.xml
            //Strings.xml helps localization of languages put into labels
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.codex_arca),
                    style = TextStyle(
                        fontSize = 24.sp,
                        lineHeight = 24.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            OutlinedTextField(
                label = {
                    Text(
                        text = stringResource(R.string.label_email),
                        color = Color.Black
                    )
                },
                value = emailText,
                onValueChange = {
                    emailText = it
                },
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color.Blue,
                    unfocusedBorderColor = Color.Blue,
                    cursorColor = Color.White,
                    focusedTextColor = Color.Green,
                    focusedLabelColor = Color.Green,
                    unfocusedTextColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    errorTextColor = Color.Red
                ),
                isError = false,
                supportingText = {

                },
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyMedium.copy(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                singleLine = true
            )
            OutlinedTextField(
                label = {
                    Text(
                        text = stringResource(R.string.label_password),
                        color = Color.Black
                    )
                },
                value = passwordText,
                onValueChange = {
                    passwordText = it
                },
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color.Blue,
                    unfocusedBorderColor = Color.Blue,
                    cursorColor = Color.White,
                    focusedTextColor = Color.Green,
                    focusedLabelColor = Color.Green,
                    unfocusedTextColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    errorTextColor = Color.Red
                ),
                isError = false,
                supportingText = {

                },
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyMedium.copy(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }),
                singleLine = true
            )

            Button(
                enabled = true,
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Cyan ,
                    disabledContainerColor = Color.Gray
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .height(65.dp)
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isValidating) {
                        CircularProgressIndicator(
                            color = Color.Gray  ,
                            strokeCap = StrokeCap.Round,
                            strokeWidth = 3.dp,
                            modifier = Modifier
                                .size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    } else {
                        Text(
                            text = "Login",
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            ),
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}



//Preview Here
@Preview
@Composable
private fun Preview() {
    LoginScreen()
}