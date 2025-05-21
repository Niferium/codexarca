package ph.niferium.codexarca.widgets

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ph.niferium.codexarca.R

@Composable
fun PrimaryButton(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    isValidating: Boolean = false,
    isEnabled: Boolean = true,
    @DrawableRes icon: Int? = null,
    onSubmit: () -> Unit = {},
) {

    Button(
        enabled = if (isValidating) false else isEnabled,
        onClick = onSubmit,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Cyan,
            disabledContainerColor = Color.Gray
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .height(65.dp)
            .padding(top = 16.dp)
            .fillMaxWidth()
        //.wrapContentWidth()
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isValidating) {
                CircularProgressIndicator(
                    color = Color.Blue,
                    strokeCap = StrokeCap.Round,
                    strokeWidth = 3.dp,
                    modifier = Modifier
                        .size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            } else {
                Text(
                    text = stringResource(text),
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        color = Color.Blue,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1
                )
                if(icon != null){
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(icon),
                        tint = Color.Cyan,
                        contentDescription = "Primary button Icon",
                        modifier = Modifier.size(20.dp)
                    )
                }

            }

        }

    }

}

@Preview
@Composable
private fun Preview() {
    PrimaryButton(
        text = R.string.button_text_sample,
        modifier = Modifier.fillMaxWidth()
    )
}