import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.components.InitAlert

fun main() = application {
    Window(
        onCloseRequest = { exitApplication() }
    ) {
        InitAlert()
    }
}