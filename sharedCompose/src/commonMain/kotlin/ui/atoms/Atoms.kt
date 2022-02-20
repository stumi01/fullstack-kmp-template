package ui.atoms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

// region defaults
val verticalPadding = 4.dp
val horizontalPadding = 8.dp
val standardPadding = Modifier.padding(horizontalPadding, verticalPadding)

//val readableDateTime = SimpleDateFormat("YYYY/MM/dd hh:mm") TODO
// endregion

@Composable
fun DButton(onClick: () -> Unit, text: String) =
    Box(modifier = Modifier.fillMaxWidth()) {
        Button(onClick, modifier = Modifier.align(Alignment.Center)) {
            Text(text)
        }
    }

@Composable
fun DColumn(content: LazyListScope.() -> Unit) =
    LazyColumn(modifier = Modifier.fillMaxWidth(), content = content)

//region Labeled input fields
@Composable
fun LabeledInputText(
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        maxLines = 1,
        readOnly = readOnly,
        enabled = !readOnly,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        label = {
            Text(text = label)
        },
        modifier = modifier then standardPadding
    )
}

@Composable
fun LabeledInputText(
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    label: String,
    text: MutableState<String>,
) = LabeledInputText(
    modifier = modifier,
    readOnly = readOnly,
    label = label,
    value = text.value,
    onValueChange = { text.value = it }
)

// endregion
