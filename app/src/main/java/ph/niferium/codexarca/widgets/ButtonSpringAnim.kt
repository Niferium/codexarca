package ph.niferium.codexarca.widgets

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

@Composable
fun ButtonSpringAnim() {
    // Basic spring animation
    var expanded by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (expanded) 1.5f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        )
    )
    var isActive1 by remember { mutableStateOf(false) }
    var isActive2 by remember { mutableStateOf(false) }
    var isActive3 by remember { mutableStateOf(false) }

    var visibilityControl by remember { mutableStateOf(false) }

    val presetScale by animateFloatAsState(
        targetValue = if (isActive1) 1.2f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessVeryLow
        )
    )

    val presetScale2 by animateFloatAsState(
        targetValue = if (isActive2) 1.5f else 0.5f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    val presetScale3 by animateFloatAsState(
        targetValue = if (isActive3) 1.5f else 0.5f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessMediumLow
        )
    )

    LaunchedEffect(visibilityControl) {
        if(visibilityControl) {
            coroutineScope {
                delay(10)
                isActive1 = !isActive1
                isActive2 = !isActive2
                isActive3 = !isActive3
            }
        }
    }
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Button with spring scale animation
        Button(
            onClick = {
                expanded = !expanded
                visibilityControl = !visibilityControl
            },
            modifier = Modifier.scale(scale),
            colors =  ButtonDefaults.buttonColors(
                containerColor = Color.Cyan,
            ),
        ) {
            Text("Spring Animated Button")
        }
        // Different spring presets
//        val springPresets = listOf(
//            "No Bounce" to Spring.DampingRatioNoBouncy,
//            "Low Bounce" to Spring.DampingRatioLowBouncy,
//            "Medium Bounce" to Spring.DampingRatioMediumBouncy,
//            "High Bounce" to Spring.DampingRatioHighBouncy
//        )
//        springPresets.forEach { (name, dampingRatio) ->
//            var isActive by remember { mutableStateOf(false) }
//            val presetScale by animateFloatAsState(
//                targetValue = if (isActive) 1.2f else 1f,
//                animationSpec = spring(
//                    dampingRatio = dampingRatio,
//                    stiffness = Spring.StiffnessMedium
//                )
//            )
//            androidx.compose.material3.Button(
//                onClick = { isActive = !isActive },
//                modifier = Modifier.scale(presetScale),
//                colors =  ButtonDefaults.buttonColors(
//                    containerColor = top_orange,
//                )
//            ) {
//                Text(
//                    color = Color.White,
//                    text = name)
//            }
//        }
        if(visibilityControl) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { isActive1 = !isActive1 },
                    modifier = Modifier.scale(presetScale),
                    colors =  ButtonDefaults.buttonColors(
                        containerColor = Color.Cyan,
                    )
                ) {
                    Text(
                        color = Color.White,
                        text = "High Bounce")
                }
//                androidx.compose.material3.Button(
//                    onClick = { isActive2 = !isActive2 },
//                    modifier = Modifier.scale(presetScale2),
//                    colors =  ButtonDefaults.buttonColors(
//                        containerColor = top_orange,
//                    )
//                ) {
//                    Text(
//                        color = Color.White,
//                        text = "High Bounce ver2 (Fast?)")
//                }
                Button(
                    onClick = { isActive3 = !isActive3 },
                    modifier = Modifier.scale(presetScale3),
                    colors =  ButtonDefaults.buttonColors(
                        containerColor = Color.Cyan,
                    )
                ) {
                    Text(
                        color = Color.White,
                        text = "High Bounce ver3")
                }
                Button(
                    onClick = { isActive3 = !isActive3 },
                    modifier = Modifier.scale(presetScale3),
                    colors =  ButtonDefaults.buttonColors(
                        containerColor = Color.Cyan,
                    )
                ) {
                    Text(
                        color = Color.White,
                        text = "High Bounce ver3")
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewBtnSpringAnim() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ButtonSpringAnim()
    }
}