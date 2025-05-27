package ph.niferium.codexarca.learning

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import ph.niferium.codexarca.MainActivity


class sample_ui_label_test {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testMainActivity() {
        //Test if labels are correctly displayed
        composeTestRule.activity.runOnUiThread {
            composeTestRule.activity.setContent {
                composeTestRule.onNodeWithText("Welcome").assertIsDisplayed()
                composeTestRule.onNode(hasText("Android 安卓 and Huawei 华为")).assertIsDisplayed()
                composeTestRule.onNode(hasText("安卓")).assertExists().assertIsDisplayed()
                composeTestRule.onNode(hasText("Huawei")).assertExists().assertIsDisplayed()
                composeTestRule.onNode(hasText("华为")).assertExists().assertIsDisplayed()
            }
            }
        }
}